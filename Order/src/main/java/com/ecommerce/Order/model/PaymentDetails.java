package com.ecommerce.Order.model;


import com.ecommerce.Order.dto.CreateOrUpdateOrderRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Data
@Entity
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String paymentMethod;  // E.g., Credit Card, PayPal, etc.
    private String paymentId;  // Unique ID from the payment provider
    private double amount;  // Payment amount
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;  // Payment status: Pending, Paid, Failed
    private Date paymentDate;  // Date when the payment was made
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")// Foreign key in CartItem table
    @JsonBackReference
    private Order order;

    @Getter
    @RequiredArgsConstructor
    public enum PaymentStatus {
        PENDING("Pending"),
        PAID("Paid"),
        FAILED("Failed");

        final private String status;

    }
}

