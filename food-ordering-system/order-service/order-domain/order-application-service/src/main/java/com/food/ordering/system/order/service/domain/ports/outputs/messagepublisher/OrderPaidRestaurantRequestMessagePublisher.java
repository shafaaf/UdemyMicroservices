package com.food.ordering.system.order.service.domain.ports.outputs.messagepublisher;

import com.food.ordering.system.domain.event.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
