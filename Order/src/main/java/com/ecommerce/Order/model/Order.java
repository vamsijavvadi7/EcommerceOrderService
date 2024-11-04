package com.ecommerce.Order.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Unique identifier for the order
    private int userId;  // Reference to the User model
    private double totalPrice;  // Total price of the order
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;  // Status of the order (e.g., "Processing", "Shipped", "Delivered")
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private ShippingDetails shippingDetails;  // Shipping address for the order
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private PaymentDetails paymentDetails;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderedItem> orderedItems=new ArrayList<OrderedItem>();

    // Helper method to add cart items
    public void addOrderedItem(OrderedItem item) {
        orderedItems.add(item);
        item.setOrder(this);
    }

    // Helper method to remove cart items
    public void removeOrderedItem(OrderedItem item) {
        orderedItems.remove(item);
        item.setOrder(null);
    };




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



}