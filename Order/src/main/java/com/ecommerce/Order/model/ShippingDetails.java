package com.ecommerce.Order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Data
@Entity
public  class ShippingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String streetAddress;  // Street address for shipping
    private String city;  // City for shipping
    private String state;  // State for shipping
    private String postalCode;  // Postal code
    private String phoneNumber;  // Phone number for delivery contact
    private Date orderDate;  // Date the order was placed
    private Date shippingDate;  // Date the order was shipped (optional)
    private Date deliveryDate;  // Date the order was delivered (optional)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")// Foreign key in CartItem table
    @JsonBackReference
    private Order order;

}