package com.ecommerce.Order.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@RequiredArgsConstructor
public class ProductCategory {
    private int id;
    private String categoryname;
    private String description;
    private List<Product> products;
}
