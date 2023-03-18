package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import com.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderRequestToRestaurantEntity(CreateOrderRequest createOrderRequest) {
        return Restaurant.builder()
                .id(new RestaurantId(createOrderRequest.getRestaurantId()))
                .products(createOrderRequest.getItems().stream().map(orderItemDto ->
                        Product.builder()
                            .id(new ProductId(orderItemDto.getProductId()))
                            .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    public Order createOrderRequestToOrderEntity(CreateOrderRequest createOrderRequest) {
        return Order.builder()
                .customerId(new CustomerId(createOrderRequest.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderRequest.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddressValueObject(createOrderRequest.getAddress()))
                .price(new Money(createOrderRequest.getPrice()))
                .orderItems(orderItemsDtosToOrderItemEntities(createOrderRequest.getItems()))
                .build();
    }

    public CreateOrderResponse orderEntityToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    private List<OrderItem> orderItemsDtosToOrderItemEntities(
            List<OrderItemDto> orderItemsDtos) {
        return orderItemsDtos.stream().map(orderItemDto ->
            OrderItem.builder()
                .product(
                    Product.builder()
                    .id(new ProductId(orderItemDto.getProductId()))
                    .build()
                )
                .price(new Money(orderItemDto.getPrice()))
                .quantity(orderItemDto.getQuantity())
                .subTotal(new Money(orderItemDto.getSubTotal()))
                .build()
        ).collect(Collectors.toList());
    }

    public TrackOrderResponse orderEntityToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private StreetAddress orderAddressToStreetAddressValueObject(OrderAddressDto orderAddressDto) {
        return StreetAddress.builder()
                .id(UUID.randomUUID())
                .street(orderAddressDto.getStreet())
                .city(orderAddressDto.getCity())
                .postalCode(orderAddressDto.getPostalCode())
                .build();
    }

}
