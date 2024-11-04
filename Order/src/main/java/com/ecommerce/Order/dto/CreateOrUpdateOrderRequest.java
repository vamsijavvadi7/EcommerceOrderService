package com.ecommerce.Order.dto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@RequiredArgsConstructor
@Component
public class CreateOrUpdateOrderRequest {

    private int userId;  // Reference to the User model
    private int cartId;  // Reference to the Cart model (used during checkout)

//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus;  // Status of the order (e.g., "Processing", "Shipped", "Delivered")

    private ShippingDetails shippingDetails;  // Shipping address for the order

    private PaymentDetails paymentDetails;

    @RequiredArgsConstructor
    @Data
    public static class PaymentDetails {
        private String paymentMethod;  // E.g., Credit Card, PayPal, etc.
        private String paymentId;  // Unique ID from the payment provider
        private double amount;  // Payment amount
        @Enumerated(EnumType.STRING)
        private PaymentStatus paymentStatus;  // Payment status: Pending, Paid, Failed
        private Date paymentDate;  // Date when the payment was made
    }


    @RequiredArgsConstructor
    @Data
    public static class ShippingDetails {
        private String streetAddress;  // Street address for shipping
        private String city;  // City for shipping
        private String state;  // State for shipping
        private String postalCode;  // Postal code
        private String phoneNumber;  // Phone number for delivery contact
        private Date orderDate;  // Date the order was placed
        private Date shippingDate;  // Date the order was shipped (optional)
        private Date deliveryDate;  // Date the order was delivered (optional)
    }
    @RequiredArgsConstructor
    public enum OrderStatus {
        PROCESSING("Processing"),
        SHIPPED("Shipped"),
        DELIVERED("Delivered"),
        CANCELLED("Cancelled");
        final private String status;



        public String getStatus() {
            return status;
        }
    }


    @RequiredArgsConstructor
    public enum PaymentStatus {
        PENDING("Pending"),
        PAID("Paid"),
        FAILED("Failed");

        final private String status;



        public String getStatus() {
            return status;
        }
    }


}