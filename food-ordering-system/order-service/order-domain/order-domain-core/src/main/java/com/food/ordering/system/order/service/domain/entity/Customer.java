package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.CustomerId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor @Getter @SuperBuilder @ToString
public class Customer extends AggregateRoot<CustomerId> {
    private String username;
    private String firstName;
    private String lastName;
}
