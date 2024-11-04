package com.ecommerce.Order.dto;
import com.ecommerce.Order.dto.getorderresponsedtos.OrderedItemWithProdInfoDto;
import com.ecommerce.Order.model.ShippingDetails;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GetOrderHistoryOrderedItemDto {
        private int id;
        private double totalPrice;
        private String orderStatus;
        private List<OrderedItemForHistory> orderedItems = new ArrayList<>();
        private ShippingDetails shippingDetails;

        @Data
        @RequiredArgsConstructor
        public static class OrderedItemForHistory {
            private int id;
            private String name;
            private byte[] productImage;
            private String brand;
            private int cartitemprice;
            private int quantityAddedInCart;
        }
    }

