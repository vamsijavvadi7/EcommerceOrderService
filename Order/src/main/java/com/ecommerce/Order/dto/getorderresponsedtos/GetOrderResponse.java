package com.ecommerce.Order.dto.getorderresponsedtos;

import com.ecommerce.Order.model.OrderedItem;
import com.ecommerce.Order.model.PaymentDetails;
import com.ecommerce.Order.model.ShippingDetails;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class GetOrderResponse {
        private int id;  // Unique identifier for the order
        private int userId;  // Reference to the User model
        private double totalPrice;  // Total price of the order
        private OrderStatus orderStatus;  // Status of the order (e.g., "Processing", "Shipped", "Delivered")
        private ShippingDetails shippingDetails;  // Shipping address for the order
        private PaymentDetails paymentDetails;
        private List<OrderedItemWithProdInfoDto> orderedItems=new ArrayList<OrderedItemWithProdInfoDto>();

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
