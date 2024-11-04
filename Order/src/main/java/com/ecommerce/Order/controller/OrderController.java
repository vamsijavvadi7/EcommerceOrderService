package com.ecommerce.Order.controller;

import com.ecommerce.Order.dto.CreateOrUpdateOrderRequest;
import com.ecommerce.Order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderservice;


    @Autowired
    CreateOrUpdateOrderRequest createOrUpdateOrderRequest;
    //place or update order
    @PostMapping("createorupdateOrder")
    public ResponseEntity<?> createorupdateOrder(@RequestBody CreateOrUpdateOrderRequest createOrUpdateOrderRequest) {
        return orderservice.createorupdateOrder(createOrUpdateOrderRequest);
    }




    //Delete Order
    @DeleteMapping("cancelOrder/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id) {
       return orderservice.deleteOrder(id);
    }


    //get order by id
    @GetMapping("getOrderById/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id) {
        return orderservice.getOrderById(id);
    }

    //get order history of a user
    @GetMapping("getOrderHistory/{userid}")
    public ResponseEntity<?> getOrderHistory(@PathVariable int userid) {
        return orderservice.getOrderHistory(userid);
    }
}
