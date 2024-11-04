package com.ecommerce.Order.service.mappers;

import com.ecommerce.Order.dto.*;
import com.ecommerce.Order.dto.getorderresponsedtos.GetOrderResponse;
import com.ecommerce.Order.dto.getorderresponsedtos.OrderedItemWithProdInfoDto;
import com.ecommerce.Order.model.Order;
import com.ecommerce.Order.model.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FrameworkMapper {

    //CreateOrUpdateOrderRequest.PaymentDetails
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "order", ignore = true),
            @Mapping(target = "paymentStatus", source = "paymentStatus")
    })
    PaymentDetails toEntity(CreateOrUpdateOrderRequest.PaymentDetails dto);

    @Mappings({
            @Mapping(target = "orderedItems", ignore = true)
    })
    GetOrderResponse toOrderDto(Order order);


    @Mappings({
            @Mapping(target = "cartitemprice", ignore = true),
            @Mapping(target = "quantityAddedInCart", ignore = true),
            @Mapping(source = "category.categoryname", target = "category"),
            @Mapping(source = "category.description", target = "categoryDescription")
    })
    OrderedItemWithProdInfoDto ToOrderedItemWithProdInfoDto(Product product);
    // Maps GetOrderResponse to GetOrderHistoryOrderedItemDto
    @Mapping(source = "orderStatus.status", target = "orderStatus") // Adjust enum to string mapping
    GetOrderHistoryOrderedItemDto mapToOrderHistory(GetOrderResponse orderResponse);
}
