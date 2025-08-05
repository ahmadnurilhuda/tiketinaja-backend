package com.greenacademy.tiketinaja.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.enums.Enum.OrderStatus;
import com.greenacademy.tiketinaja.enums.Enum.PaymentStatus;
import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.Order;
import com.greenacademy.tiketinaja.models.OrderItem;
import com.greenacademy.tiketinaja.models.Payment;
import com.greenacademy.tiketinaja.models.Ticket;
import com.greenacademy.tiketinaja.models.TicketType;
import com.greenacademy.tiketinaja.models.TransactionType;
import com.greenacademy.tiketinaja.repositories.OrderRepository;
import com.greenacademy.tiketinaja.repositories.PaymentRepository;
import com.greenacademy.tiketinaja.repositories.TicketRepository;
import com.greenacademy.tiketinaja.repositories.TicketTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final OrderService orderService;
    private final TicketTypeService ticketTypeService;
    private final TicketTypeRepository ticketTypeRepo;
    private final OrderRepository orderRepo;
    private final OrderItemService orderItemService;
    private final TransactionTypeService transactionTypeService;
    private final TicketRepository ticketRepo;

    public PaymentService(PaymentRepository paymentRepo, OrderService orderService,
            TransactionTypeService transactionTypeService, OrderRepository orderRepo, OrderItemService orderItemService,
            TicketRepository ticketRepo, TicketTypeService ticketTypeService, TicketTypeRepository ticketTypeRepo) {
        this.paymentRepo = paymentRepo;
        this.orderService = orderService;
        this.orderRepo = orderRepo;
        this.transactionTypeService = transactionTypeService;
        this.orderItemService = orderItemService;
        this.ticketRepo = ticketRepo;
        this.ticketTypeService = ticketTypeService;
        this.ticketTypeRepo = ticketTypeRepo;
    }

    public Payment getPayment(Integer orderId) {
        return paymentRepo.findByOrderId(orderId).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    public Map<String, Object> checkSignature(String midtransServerKey, Map<String, Object> payload) {
        String orderId = (String) payload.get("order_id");
        String statusCode = (String) payload.get("status_code");
        String grossAmount = (String) payload.get("gross_amount");
        String receivedSignature = (String) payload.get("signature_key");
        String stringToHash = orderId + statusCode + grossAmount + midtransServerKey;
        System.out.println("\n\n\nstringToHash: " + stringToHash);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = digest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String calculatedSignature = hexString.toString();

            if (!calculatedSignature.equals(receivedSignature)) {
                throw new SecurityException("Signature key invalid");
            }

            System.out.println("\n\n\ncalculatedSignature: " + calculatedSignature);
            System.out.println("receivedSignature  : " + receivedSignature);

            return payload;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to check signature: " + e.getMessage(), e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Transactional
    public void savePayment(Map<String, Object> payload, String midtransServerKey) {
        System.out.println("\n\n\nPayload di savePayment: " + payload);
        String orderIdRaw = (String) payload.get("order_id");
        String transactionId = (String) payload.get("transaction_id");
        String transactionStatus = (String) payload.get("transaction_status");
        String transactionType = (String) payload.get("payment_type");
        Integer orderId = Integer.valueOf(orderIdRaw.replace("ORDER-", ""));

        Payment payment = getPayment(orderId);

        if (!"settlement".equals(transactionStatus) && !"capture".equals(transactionStatus)) {
            payment.setTransactionIdGateway(transactionId);
            payment.setStatus(PaymentStatus.FAILED);
            TransactionType transactionTypeExist = transactionTypeService.getByName(transactionType);
            payment.setTransactionType(transactionTypeExist);
            paymentRepo.save(payment);
            
            Order order = orderService.getOrder(orderId);
            order.setStatus(OrderStatus.FAILED);
            orderRepo.save(order);
            throw new IllegalArgumentException("Payment failed with status: " + transactionStatus);
        }
        payment.setTransactionIdGateway(transactionId);
        payment.setStatus(PaymentStatus.SUCCESS);
        TransactionType transactionTypeExist = transactionTypeService.getByName(transactionType);
        payment.setTransactionType(transactionTypeExist);
        payment.setPaidAt(Instant.now());
        paymentRepo.save(payment);

        Order order = orderService.getOrder(orderId);
        order.setStatus(OrderStatus.PAID);
        orderRepo.save(order);

        List<OrderItem> items = orderItemService.getAllByOrderId(orderId);
        for (OrderItem item : items) {
            TicketType ticketType = ticketTypeService.getTicketTypeByOrderItem(item.getId());
            if (ticketType.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException(
                        ticketType.getName() + " from " + ticketType.getEvent().getTitle() + " quantity not enough");
            }
            ticketType.setQuantity(ticketType.getQuantity() - item.getQuantity());
            ticketTypeRepo.save(ticketType);
            for (int i = 0; i < item.getQuantity(); i++) {
                Ticket ticket = new Ticket();
                ticket.setOrderItem(item);
                ticket.setUniqueCode(UUID.randomUUID().toString());
                ticket.setIsUsed(false);
                ticketRepo.save(ticket);
            }
        }
    }

}
