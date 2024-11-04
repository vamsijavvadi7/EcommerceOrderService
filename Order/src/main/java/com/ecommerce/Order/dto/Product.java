package com.ecommerce.Order.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Product {
    private int id;
    private String name;
    @Lob // Marks this field as a large object, appropriate for binary data
    private byte[] productImage; // field for storing image as binary data
    private String brand;
    private ProductCategory category;
    private int quantity;
    private String description;
    private int price;
}
