package com.ecommerce.Order.service.mappers;

import com.ecommerce.Order.dto.CreateOrUpdateOrderRequest;
import com.ecommerce.Order.dto.GetCartReponseCartItem;
import com.ecommerce.Order.model.OrderedItem;
import com.ecommerce.Order.model.ShippingDetails;
import org.springframework.stereotype.Component;

@Component
public class ManualMappersForDtoToModelConversion {
    // Method to convert DTO ShippingDetails to Model ShippingDetails
    public ShippingDetails mapToShippingDetailsEntity(CreateOrUpdateOrderRequest.ShippingDetails dtoShippingDetails) {
        ShippingDetails shippingDetails=new ShippingDetails();
        shippingDetails.setStreetAddress(dtoShippingDetails.getStreetAddress());
        shippingDetails.setCity(dtoShippingDetails.getCity());
        shippingDetails.setState(dtoShippingDetails.getState());
        shippingDetails.setPostalCode(dtoShippingDetails.getPostalCode());
        shippingDetails.setPhoneNumber(dtoShippingDetails.getPhoneNumber());
        shippingDetails.setOrderDate(dtoShippingDetails.getOrderDate());
        shippingDetails.setShippingDate(dtoShippingDetails.getShippingDate());
        shippingDetails.setDeliveryDate(dtoShippingDetails.getDeliveryDate());

        return shippingDetails;
    }

    public OrderedItem mapToOrderedItemEntity(GetCartReponseCartItem getCartReponseCartItem) {
        OrderedItem orderedItem=new OrderedItem();
        orderedItem.setProductId(getCartReponseCartItem.getProductId());
        orderedItem.setPrice(getCartReponseCartItem.getCartitemprice());
        orderedItem.setQuantityAddedInCart(getCartReponseCartItem.getQuantityAddedInCart());


        return orderedItem;
    }
}
