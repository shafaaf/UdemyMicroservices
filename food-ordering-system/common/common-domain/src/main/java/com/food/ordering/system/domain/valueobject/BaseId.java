package com.food.ordering.system.domain.valueobject;

import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class BaseId <T> {
    @Getter
    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }
}
