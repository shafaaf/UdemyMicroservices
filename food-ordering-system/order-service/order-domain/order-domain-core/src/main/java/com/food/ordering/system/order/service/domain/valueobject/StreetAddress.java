package com.food.ordering.system.order.service.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor @Getter @EqualsAndHashCode @Builder
public class StreetAddress {
    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;
}
