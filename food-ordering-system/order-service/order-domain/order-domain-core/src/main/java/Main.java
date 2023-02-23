import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.order.service.domain.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;

class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Product product = Product.builder().
                name("table").
                price(new Money(new BigDecimal(100)))
                .id(new ProductId(UUID.randomUUID()))
                .build();
        System.out.println(product.toString());
    }
}
