package com.ecommerce.Order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient("cart-service")
public interface CartInterface {


    //get cart by id
    @GetMapping("cart/getCart/{userid}")
    public ResponseEntity<?> getCartByUserid(@PathVariable int userid);
    //get cart by userid
    @GetMapping("cart/getCartPrice/{userid}")
    public ResponseEntity<?> getCartPriceByUserid(@PathVariable int userid);

    //Delete CartItems of a cart
    @DeleteMapping("cart/clearCart/{cartid}")
    public void clearCart(@PathVariable int cartid);

}
