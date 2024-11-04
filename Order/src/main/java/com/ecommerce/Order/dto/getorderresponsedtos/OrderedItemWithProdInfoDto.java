package com.ecommerce.Order.dto.getorderresponsedtos;

import com.ecommerce.Order.dto.ProductCategory;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderedItemWithProdInfoDto {
        private int id;
        private String name;
        @Lob // Marks this field as a large object, appropriate for binary data
        private byte[] productImage; // field for storing image as binary data
        private String brand;
        private String  category;
        private int quantity;
        private String description;
        private String categoryDescription;
        private int price;
        private int cartitemprice;
        private int quantityAddedInCart;
}
