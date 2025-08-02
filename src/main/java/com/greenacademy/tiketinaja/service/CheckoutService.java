package com.greenacademy.tiketinaja.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.midtrans.service.MidtransSnapApi;

import jakarta.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenacademy.tiketinaja.dto.request.CheckoutRequest;
import com.greenacademy.tiketinaja.dto.request.TicketTypeOrderItemRequest;
import com.greenacademy.tiketinaja.dto.response.CheckoutResponse;
import com.greenacademy.tiketinaja.enums.Enum.OrderStatus;
import com.greenacademy.tiketinaja.enums.Enum.PaymentStatus;
import com.greenacademy.tiketinaja.models.Order;
import com.greenacademy.tiketinaja.models.OrderItem;
import com.greenacademy.tiketinaja.models.Payment;
import com.greenacademy.tiketinaja.models.TicketType;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.repositories.OrderItemRepository;
import com.greenacademy.tiketinaja.repositories.OrderRepository;
import com.greenacademy.tiketinaja.repositories.PaymentRepository;

@Service
public class CheckoutService {

    private final MidtransSnapApi midtransSnapApi;
    private final TicketTypeService ticketTypeService;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final PaymentRepository paymentRepo;

    public CheckoutService(MidtransSnapApi midtransSnapApi, TicketTypeService ticketTypeService,
            ObjectMapper objectMapper, OrderRepository orderRepo, OrderItemRepository orderItemRepo,
            UserService userService, PaymentRepository paymentRepo) {
        this.midtransSnapApi = midtransSnapApi;
        this.objectMapper = objectMapper;
        this.ticketTypeService = ticketTypeService;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.userService = userService;
        this.paymentRepo = paymentRepo;
    }

    @Transactional
    public CheckoutResponse checkout(User user, CheckoutRequest checkoutRequest) {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        List<Map<String, Object>> snapshot = new ArrayList<>();

        for (TicketTypeOrderItemRequest item : checkoutRequest.getItems()) {
            TicketType ticket = ticketTypeService.getTicketType(item.getTicketTypeId());

            Instant now = Instant.now();
            if (ticket.getStartDate().isAfter(now)) {
                throw new IllegalArgumentException("Ticket type not available");
            }
            if (ticket.getEndDate().isBefore(now)) {
                throw new IllegalArgumentException("Ticket type expired");
            }
            if (ticket.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Quantity not enough");
            }
            BigDecimal subtotal = ticket.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setTicketTypeId(ticket);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPerItem(subtotal);
            orderItems.add(orderItem);

            Map<String, Object> snapItem = new HashMap<>();
            snapItem.put("ticketTypeId", ticket.getId());
            snapItem.put("name", ticket.getName());
            snapItem.put("price", ticket.getPrice());
            snapItem.put("quantity", item.getQuantity());
            snapshot.add(snapItem);
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        
        try {
            String metaJson = objectMapper.writeValueAsString(snapshot);
            order.setMeta(metaJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Gagal serialize meta snapshot", e);
        }

        order = orderRepo.save(order);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepo.save(payment);

        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepo.save(item);
        }
        return createMidtransTransaction(order);
    }

    private CheckoutResponse createMidtransTransaction(Order order) {
        User user = userService.getProfile(order.getUser().getId());

        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", "ORDER-" + order.getId());
        transactionDetails.put("gross_amount", order.getTotalAmount());

        Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("first_name", user.getFullName());
        customerDetails.put("email", user.getEmail());
        customerDetails.put("phone", user.getPhoneNumber());

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_details", transactionDetails);
        params.put("customer_details", customerDetails);

        try {
            JSONObject response = midtransSnapApi.createTransaction(params);
            Map<String, Object> responseMap = response.toMap();
            Map<String, String> stringResponseMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
                stringResponseMap.put(entry.getKey(), entry.getValue().toString());
            }
            String redirectUrl = stringResponseMap.get("redirect_url");
            return new CheckoutResponse(order.getId(), redirectUrl);
        } catch (Exception e) {
            throw new RuntimeException("Gagal membuat transaksi midtrans", e);
        }
    }
}
