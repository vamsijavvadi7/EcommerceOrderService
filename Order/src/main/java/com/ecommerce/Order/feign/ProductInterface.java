package com.ecommerce.Order.feign;


import com.ecommerce.Order.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("product")
public interface ProductInterface {
    //Servlet to get product ny id
    @GetMapping("/product/getProduct/{id}")
    public Optional<Product> getProductByID(@PathVariable int id);
}
