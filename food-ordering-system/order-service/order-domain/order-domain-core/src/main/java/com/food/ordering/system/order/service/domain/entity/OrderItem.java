package com.food.ordering.system.order.service.domain.entity;


import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

// @SuperBuilder allows us to build orderitemid as well in builder function
@AllArgsConstructor @Getter @SuperBuilder @ToString
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal; //  quantity * price

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }

    boolean validateItem() {
        boolean res = price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
        if (!res) {
            throw new OrderDomainException("Order item price: " + price.getAmount() +
                    " is not valid for product: " + product.getPrice());
        }
        return true;
    }
}
