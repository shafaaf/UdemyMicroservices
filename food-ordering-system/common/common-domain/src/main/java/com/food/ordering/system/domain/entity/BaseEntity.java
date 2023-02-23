package com.food.ordering.system.domain.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @EqualsAndHashCode @SuperBuilder @NoArgsConstructor
public abstract class BaseEntity<ID> {
    private ID id;
}
