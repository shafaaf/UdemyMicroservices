package com.food.ordering.system.order.service.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor @Getter @Setter @SuperBuilder
public class OrderPaidEvent extends OrderEvent {
}
