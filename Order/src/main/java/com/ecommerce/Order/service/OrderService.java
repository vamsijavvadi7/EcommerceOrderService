package com.ecommerce.Order.service;

import com.ecommerce.Order.dao.OrderRepo;
import com.ecommerce.Order.dto.CreateOrUpdateOrderRequest;
import com.ecommerce.Order.dto.GetCartResponse;
import com.ecommerce.Order.dto.GetOrderHistoryOrderedItemDto;
import com.ecommerce.Order.dto.Product;
import com.ecommerce.Order.dto.getorderresponsedtos.GetOrderResponse;
import com.ecommerce.Order.dto.getorderresponsedtos.OrderedItemWithProdInfoDto;
import com.ecommerce.Order.feign.CartInterface;
import com.ecommerce.Order.feign.ProductInterface;
import com.ecommerce.Order.model.Order;
import com.ecommerce.Order.model.OrderedItem;
import com.ecommerce.Order.model.PaymentDetails;
import com.ecommerce.Order.model.ShippingDetails;
import com.ecommerce.Order.service.mappers.FrameworkMapper;
import com.ecommerce.Order.service.mappers.ManualMappersForDtoToModelConversion;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ManualMappersForDtoToModelConversion manualMappersFromDtoToModels;

    @Autowired
    FrameworkMapper frameworkMapper;

    @Autowired
    CartInterface cartInterface;

    @Autowired
    ProductInterface productInterface;

    @Transactional
    public ResponseEntity<?> createorupdateOrder(CreateOrUpdateOrderRequest createOrUpdateOrderRequest) {
        Order order = new Order();

//Get cartprice from cart service
        ResponseEntity<?> totalCartPriceResponse;
        ResponseEntity<?> getCartReponseEntity;
        //As we use user id a lot in this we create a local variable to store would be better
        int userid=createOrUpdateOrderRequest.getUserId();
        try {
            // Attempt to fetch cart price by user ID
            totalCartPriceResponse = cartInterface.getCartPriceByUserid(userid);
            getCartReponseEntity=cartInterface.getCartByUserid(userid);
        } catch (FeignException.NotFound e) {
            // Handle 404 error from the Feign client
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart not found for user with ID: " + userid);
        }catch (FeignException.ServiceUnavailable e) {
            // Handle 404 error from the Feign client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Cart service is down try again later.");
        }
        //skipping the userid associated cart check to decrease api calls as we are already checking user id associated cart through  getcartprice api
        order.setUserId(userid);
    //lets use this getCartReponseEntity to transfer cartitems into ordered items
        // Process the cart response entity
        GetCartResponse getCartResponse = null;
        if (getCartReponseEntity.getStatusCode() == HttpStatus.OK) {
            Object responseBody = getCartReponseEntity.getBody();
            if (responseBody instanceof LinkedHashMap<?,?>) {
                // Convert LinkedHashMap to GetCartResponse using ObjectMapper
                getCartResponse = objectMapper.convertValue(responseBody, GetCartResponse.class);
            } else if (responseBody instanceof GetCartResponse) {
                getCartResponse = (GetCartResponse) responseBody;
            }
        }

        // Map cart items to ordered items if getCartResponse is not null
        if (getCartResponse != null) {
            if(getCartResponse.getCartitems().isEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cart is Empty add items to cart");


            List<OrderedItem> orderedItems = getCartResponse.getCartitems().stream()
                    .map(manualMappersFromDtoToModels::mapToOrderedItemEntity)
                    .collect(Collectors.toList());
            orderedItems.forEach(order::addOrderedItem);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Cart is unavailable for user ID: " + userid);
        }

        //setting total price
        if (totalCartPriceResponse.getStatusCode() == HttpStatus.OK) {
            Integer totalCartPrice = (Integer) totalCartPriceResponse.getBody();
            if (totalCartPrice != null) {
                order.setTotalPrice(totalCartPrice);
            }else {
                // Send a 204 No Content if the price is missing
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("Cart price not available for user ID: " + userid);
            }

        }
            order.setOrderStatus(Order.OrderStatus.PROCESSING);
        //setting shipping details
        ShippingDetails shippingDetails = manualMappersFromDtoToModels.mapToShippingDetailsEntity(createOrUpdateOrderRequest.getShippingDetails());
        shippingDetails.setOrder(order);
        order.setShippingDetails(shippingDetails);

        //setting payment details
        PaymentDetails paymentDetails = frameworkMapper.toEntity(createOrUpdateOrderRequest.getPaymentDetails());
        paymentDetails.setAmount(order.getTotalPrice());
        paymentDetails.setOrder(order);
        order.setPaymentDetails(paymentDetails);
        try {
            Order savedOrder = orderRepo.save(order);
            Optional<Order> fetchedOrder = orderRepo.findById(savedOrder.getId());

            if (fetchedOrder.isPresent()) {
                cartInterface.clearCart(createOrUpdateOrderRequest.getCartId());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to save the order");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving order: " + e.getMessage());
        }



        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    //cancel order
    public ResponseEntity<?> deleteOrder(int id) {

        Optional<Order> orderOptional = orderRepo.findById(id);
        orderOptional.ifPresent(order -> {
            order.setOrderStatus(Order.OrderStatus.CANCELLED);
            orderRepo.save(order);
        });
        return ResponseEntity.status(HttpStatus.OK).body("Order Cancelled");

    }

    public ResponseEntity<?> getOrderById(int id) {
        Optional<Order> orderOptional = orderRepo.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            GetOrderResponse getOrderResponse=frameworkMapper.toOrderDto(order);
            List<OrderedItemWithProdInfoDto> orderedItemWithProdInfoDtos=order.getOrderedItems().stream().map(orderedItem -> {
                Optional<Product> productresponse=productInterface.getProductByID(orderedItem.getProductId());

                if (productresponse.isPresent()) {
                    Product product = productresponse.get();
                    OrderedItemWithProdInfoDto orderedItemWithProdInfoDto=frameworkMapper.ToOrderedItemWithProdInfoDto(product);
                    orderedItemWithProdInfoDto.setQuantityAddedInCart(orderedItem.getQuantityAddedInCart());
                    orderedItemWithProdInfoDto.setId(orderedItem.getId());
                    orderedItemWithProdInfoDto.setPrice(orderedItem.getPrice());
                    orderedItemWithProdInfoDto.setCartitemprice(orderedItem.getPrice());
                    return orderedItemWithProdInfoDto;
                }
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with Id"+orderedItem.getProductId());
               return null;
           }).collect(Collectors.toList());


            getOrderResponse.setOrderedItems(orderedItemWithProdInfoDtos);
            return ResponseEntity.status(HttpStatus.OK).body(getOrderResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found with this id: " + id);
        }
    }


    public ResponseEntity<?> getOrderHistory(int userid) {
        Optional<List<Order>> fetchedOrder = orderRepo.findByUserId(userid);
        List<GetOrderHistoryOrderedItemDto> getOrderHistoryOrderedItemDtosList;
        if (fetchedOrder.isPresent()) {
            getOrderHistoryOrderedItemDtosList= fetchedOrder.get().stream().map(order-> {
    ResponseEntity<?> getOrderReponseEntity= getOrderById(order.getId());
        if (getOrderReponseEntity.getStatusCode() == HttpStatus.OK) {
            GetOrderResponse getOrderResponse = (GetOrderResponse) getOrderReponseEntity.getBody();
             return frameworkMapper.mapToOrderHistory(getOrderResponse);
        }
        return null;
}).collect(Collectors.toList());


    return ResponseEntity.status(HttpStatus.OK).body(getOrderHistoryOrderedItemDtosList);
        }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No order history found with useid");
    }
}

