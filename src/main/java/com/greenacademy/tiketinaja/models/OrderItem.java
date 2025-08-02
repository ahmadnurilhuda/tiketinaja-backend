package com.greenacademy.tiketinaja.models;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPerItem;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public OrderItem() {
    }
    public OrderItem(Integer id, Order order, TicketType ticketType, Integer quantity, BigDecimal totalPerItem) {
        this.id = id;
        this.order = order;
        this.ticketType = ticketType;
        this.quantity = quantity;
        this.totalPerItem = totalPerItem;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public TicketType getTicketType() {
        return ticketType;
    }
    public void setTicketTypeId(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPerItem() {
        return totalPerItem;
    }
    public void setTotalPerItem(BigDecimal totalPerItem) {
        this.totalPerItem = totalPerItem;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }  
}

// Table OrderItems {
//   id int [pk, increment]
//   order_id int [not null, ref: > Orders.id]
//   ticket_type_id int [not null, ref: > TicketTypes.id]
//   quantity int [not null] 
//   total_per_item decimal(10, 2) [not null, note: 'Harga tiket saat dibeli']
//   created_at timestamp [default: `now()`]
// }
