package com.food.ordering.system.order.service.domain.entity;


import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

// @SuperBuilder allows us to build orderitemid as well in builder function
@AllArgsConstructor @Getter @SuperBuilder @ToString
public class OrderItem extends BaseEntity<OrderItemId> {
    private final OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal; //  quantity * price
}
