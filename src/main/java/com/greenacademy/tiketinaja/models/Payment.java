package com.greenacademy.tiketinaja.models;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.greenacademy.tiketinaja.enums.Enum.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_id_gateway", nullable = false)
    private String transactionIdGateway;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status", columnDefinition = "enum('pending', 'success', 'failed') default 'pending'")
    private PaymentStatus status;

    @Column(name = "paid_at")
    private Instant paidAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;


    public Payment() {
    }
    
    public Payment(Integer id, Order order, TransactionType transactionType, BigDecimal amount, String transactionIdGateway, PaymentStatus status, Instant paidAt) {
        this.id = id;
        this.order = order;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionIdGateway = transactionIdGateway;
        this.status = status;
        this.paidAt = paidAt;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionIdGateway() {
        return transactionIdGateway;
    }

    public void setTransactionIdGateway(String transactionIdGateway) {
        this.transactionIdGateway = transactionIdGateway;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
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

// Table Payments {
//   id int [pk, increment]
//   order_id int [unique, not null, ref: > Orders.id]
//   transaction_type_id int [unique, not null, ref: > TransactionTypes.id]
//   amount decimal(12, 2) [not null]
//   transaction_id_gateway varchar(255) [note: 'ID dari payment gateway']
//   status PaymentStatus [not null, default: 'pending']
//   paid_at timestamp
//   created_at timestamp [default: `now()`]
// }