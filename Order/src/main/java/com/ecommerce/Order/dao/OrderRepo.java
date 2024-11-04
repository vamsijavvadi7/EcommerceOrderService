package com.ecommerce.Order.dao;

import com.ecommerce.Order.model.Order;
import com.ecommerce.Order.model.PaymentDetails;
import com.ecommerce.Order.model.ShippingDetails;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Integer> {
    @Query("SELECT o.shippingDetails FROM Order o WHERE o.id = :orderId")
    Optional<ShippingDetails> findShippingDetailsByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT o.paymentDetails FROM Order o WHERE o.id = :orderId")
    Optional<PaymentDetails> findPaymentDetailsByOrderId(@Param("orderId") Integer orderId);

    Optional<List<Order>> findByUserId(int userid);
}
