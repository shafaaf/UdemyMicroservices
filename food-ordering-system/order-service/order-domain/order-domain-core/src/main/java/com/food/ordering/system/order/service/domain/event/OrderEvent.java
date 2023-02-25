package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@AllArgsConstructor @SuperBuilder @NoArgsConstructor
public abstract class OrderEvent implements DomainEvent<Order> {
    private Order order;
    private ZonedDateTime createdAt;
}
