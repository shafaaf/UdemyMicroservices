package com.food.ordering.system.order.service.domain.ports.outputs.messagepublisher;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.event.OrderCancelEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEvent<OrderCancelEvent> {
}
