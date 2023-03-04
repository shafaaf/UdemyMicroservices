package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
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

    public Restaurant createOrderRequestToRestaurant(CreateOrderRequest createOrderRequest) {
        return Restaurant.builder()
                .id(new RestaurantId(createOrderRequest.getRestaurantId()))
                .products(createOrderRequest.getItems().stream().map(orderItemDto ->
                        Product.builder()
                            .id(new ProductId(orderItemDto.getProductId()))
                            .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    public Order createOrderRequestToOrder(CreateOrderRequest createOrderRequest) {
        return Order.builder()
                .customerId(new CustomerId(createOrderRequest.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderRequest.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderRequest.getAddress()))
                .price(new Money(createOrderRequest.getPrice()))
                .orderItems(orderItemsToOrderItemEntities(createOrderRequest.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<OrderItemDto> orderItemDtos) {
        return orderItemDtos.stream().map(orderItemDto ->
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

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return StreetAddress.builder()
                .id(UUID.randomUUID())
                .street(orderAddress.getStreet())
                .city(orderAddress.getCity())
                .postalCode(orderAddress.getPostalCode())
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
