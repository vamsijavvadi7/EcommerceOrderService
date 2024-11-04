package com.ecommerce.Order.service.mappers;

import com.ecommerce.Order.dto.CreateOrUpdateOrderRequest;
import com.ecommerce.Order.dto.GetOrderHistoryOrderedItemDto;
import com.ecommerce.Order.dto.Product;
import com.ecommerce.Order.dto.ProductCategory;
import com.ecommerce.Order.dto.getorderresponsedtos.GetOrderResponse;
import com.ecommerce.Order.dto.getorderresponsedtos.OrderedItemWithProdInfoDto;
import com.ecommerce.Order.model.Order;
import com.ecommerce.Order.model.PaymentDetails;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-04T02:16:23-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class FrameworkMapperImpl implements FrameworkMapper {

    @Override
    public PaymentDetails toEntity(CreateOrUpdateOrderRequest.PaymentDetails dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentDetails paymentDetails = new PaymentDetails();

        paymentDetails.setPaymentStatus( paymentStatusToPaymentStatus( dto.getPaymentStatus() ) );
        paymentDetails.setPaymentMethod( dto.getPaymentMethod() );
        paymentDetails.setPaymentId( dto.getPaymentId() );
        paymentDetails.setAmount( dto.getAmount() );
        paymentDetails.setPaymentDate( dto.getPaymentDate() );

        return paymentDetails;
    }

    @Override
    public GetOrderResponse toOrderDto(Order order) {
        if ( order == null ) {
            return null;
        }

        GetOrderResponse getOrderResponse = new GetOrderResponse();

        getOrderResponse.setId( order.getId() );
        getOrderResponse.setUserId( order.getUserId() );
        getOrderResponse.setTotalPrice( order.getTotalPrice() );
        getOrderResponse.setOrderStatus( orderStatusToOrderStatus( order.getOrderStatus() ) );
        getOrderResponse.setShippingDetails( order.getShippingDetails() );
        getOrderResponse.setPaymentDetails( order.getPaymentDetails() );

        return getOrderResponse;
    }

    @Override
    public OrderedItemWithProdInfoDto ToOrderedItemWithProdInfoDto(Product product) {
        if ( product == null ) {
            return null;
        }

        OrderedItemWithProdInfoDto orderedItemWithProdInfoDto = new OrderedItemWithProdInfoDto();

        orderedItemWithProdInfoDto.setCategory( productCategoryCategoryname( product ) );
        orderedItemWithProdInfoDto.setCategoryDescription( productCategoryDescription( product ) );
        orderedItemWithProdInfoDto.setId( product.getId() );
        orderedItemWithProdInfoDto.setName( product.getName() );
        byte[] productImage = product.getProductImage();
        if ( productImage != null ) {
            orderedItemWithProdInfoDto.setProductImage( Arrays.copyOf( productImage, productImage.length ) );
        }
        orderedItemWithProdInfoDto.setBrand( product.getBrand() );
        orderedItemWithProdInfoDto.setQuantity( product.getQuantity() );
        orderedItemWithProdInfoDto.setDescription( product.getDescription() );
        orderedItemWithProdInfoDto.setPrice( product.getPrice() );

        return orderedItemWithProdInfoDto;
    }

    @Override
    public GetOrderHistoryOrderedItemDto mapToOrderHistory(GetOrderResponse orderResponse) {
        if ( orderResponse == null ) {
            return null;
        }

        GetOrderHistoryOrderedItemDto getOrderHistoryOrderedItemDto = new GetOrderHistoryOrderedItemDto();

        getOrderHistoryOrderedItemDto.setOrderStatus( orderResponseOrderStatusStatus( orderResponse ) );
        getOrderHistoryOrderedItemDto.setId( orderResponse.getId() );
        getOrderHistoryOrderedItemDto.setTotalPrice( orderResponse.getTotalPrice() );
        getOrderHistoryOrderedItemDto.setOrderedItems( orderedItemWithProdInfoDtoListToOrderedItemForHistoryList( orderResponse.getOrderedItems() ) );
        getOrderHistoryOrderedItemDto.setShippingDetails( orderResponse.getShippingDetails() );

        return getOrderHistoryOrderedItemDto;
    }

    protected PaymentDetails.PaymentStatus paymentStatusToPaymentStatus(CreateOrUpdateOrderRequest.PaymentStatus paymentStatus) {
        if ( paymentStatus == null ) {
            return null;
        }

        PaymentDetails.PaymentStatus paymentStatus1;

        switch ( paymentStatus ) {
            case PENDING: paymentStatus1 = PaymentDetails.PaymentStatus.PENDING;
            break;
            case PAID: paymentStatus1 = PaymentDetails.PaymentStatus.PAID;
            break;
            case FAILED: paymentStatus1 = PaymentDetails.PaymentStatus.FAILED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + paymentStatus );
        }

        return paymentStatus1;
    }

    protected GetOrderResponse.OrderStatus orderStatusToOrderStatus(Order.OrderStatus orderStatus) {
        if ( orderStatus == null ) {
            return null;
        }

        GetOrderResponse.OrderStatus orderStatus1;

        switch ( orderStatus ) {
            case PROCESSING: orderStatus1 = GetOrderResponse.OrderStatus.PROCESSING;
            break;
            case SHIPPED: orderStatus1 = GetOrderResponse.OrderStatus.SHIPPED;
            break;
            case DELIVERED: orderStatus1 = GetOrderResponse.OrderStatus.DELIVERED;
            break;
            case CANCELLED: orderStatus1 = GetOrderResponse.OrderStatus.CANCELLED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderStatus );
        }

        return orderStatus1;
    }

    private String productCategoryCategoryname(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String categoryname = category.getCategoryname();
        if ( categoryname == null ) {
            return null;
        }
        return categoryname;
    }

    private String productCategoryDescription(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String description = category.getDescription();
        if ( description == null ) {
            return null;
        }
        return description;
    }

    private String orderResponseOrderStatusStatus(GetOrderResponse getOrderResponse) {
        if ( getOrderResponse == null ) {
            return null;
        }
        GetOrderResponse.OrderStatus orderStatus = getOrderResponse.getOrderStatus();
        if ( orderStatus == null ) {
            return null;
        }
        String status = orderStatus.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    protected GetOrderHistoryOrderedItemDto.OrderedItemForHistory orderedItemWithProdInfoDtoToOrderedItemForHistory(OrderedItemWithProdInfoDto orderedItemWithProdInfoDto) {
        if ( orderedItemWithProdInfoDto == null ) {
            return null;
        }

        GetOrderHistoryOrderedItemDto.OrderedItemForHistory orderedItemForHistory = new GetOrderHistoryOrderedItemDto.OrderedItemForHistory();

        orderedItemForHistory.setId( orderedItemWithProdInfoDto.getId() );
        orderedItemForHistory.setName( orderedItemWithProdInfoDto.getName() );
        byte[] productImage = orderedItemWithProdInfoDto.getProductImage();
        if ( productImage != null ) {
            orderedItemForHistory.setProductImage( Arrays.copyOf( productImage, productImage.length ) );
        }
        orderedItemForHistory.setBrand( orderedItemWithProdInfoDto.getBrand() );
        orderedItemForHistory.setCartitemprice( orderedItemWithProdInfoDto.getCartitemprice() );
        orderedItemForHistory.setQuantityAddedInCart( orderedItemWithProdInfoDto.getQuantityAddedInCart() );

        return orderedItemForHistory;
    }

    protected List<GetOrderHistoryOrderedItemDto.OrderedItemForHistory> orderedItemWithProdInfoDtoListToOrderedItemForHistoryList(List<OrderedItemWithProdInfoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<GetOrderHistoryOrderedItemDto.OrderedItemForHistory> list1 = new ArrayList<GetOrderHistoryOrderedItemDto.OrderedItemForHistory>( list.size() );
        for ( OrderedItemWithProdInfoDto orderedItemWithProdInfoDto : list ) {
            list1.add( orderedItemWithProdInfoDtoToOrderedItemForHistory( orderedItemWithProdInfoDto ) );
        }

        return list1;
    }
}
