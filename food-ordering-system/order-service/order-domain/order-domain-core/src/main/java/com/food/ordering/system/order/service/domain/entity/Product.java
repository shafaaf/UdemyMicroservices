package com.food.ordering.system.order.service.domain.entity;


import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor @Getter @SuperBuilder @EqualsAndHashCode @ToString
public class Product extends BaseEntity<ProductId> {
    private final String name;
    private final Money price;
}
