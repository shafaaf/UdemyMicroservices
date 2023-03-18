package com.food.ordering.system.domain.event;

public interface DomainEventPublisher<T> {

    void publish(T domainEvent);
}
