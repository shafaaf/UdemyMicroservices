package com.food.ordering.system.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString @AllArgsConstructor @Getter
public abstract class BaseId <T> {
    private final T value;
}
