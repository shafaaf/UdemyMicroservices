import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;

import java.math.BigDecimal;
import java.util.UUID;

class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Product product = Product.builder()
                .name("table")
                .price(new Money(new BigDecimal(42)))
                .id(new ProductId(UUID.randomUUID()))
                .build();
        System.out.println(product.toString());

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .quantity(3)
                .price(new Money(new BigDecimal(23)))
                .id(new OrderItemId(1L))
                .orderId(new OrderId(UUID.randomUUID()))
                .build();
        System.out.println(orderItem.toString());
    }
}
