package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.order.service.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor @Getter
public abstract class OrderEvent {
    private Order order;
    private ZonedDateTime createdAt;
}
