package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddressDto;
import com.food.ordering.system.order.service.domain.dto.create.OrderItemDto;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.inputs.OrderApplicationService;
import com.food.ordering.system.order.service.domain.ports.outputs.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.outputs.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.outputs.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderRequest createOrderRequest;
    private CreateOrderRequest createOrderRequestWrongTotalPrice;
    private CreateOrderRequest createOrderRequestWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45");
    private final UUID PRODUCT_ID_1 = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private final UUID PRODUCT_ID_2 = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb49");
    private final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");

    private Restaurant restaurant;

    @BeforeEach
    public void init() {
        createOrderRequest = CreateOrderRequest.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddressDto.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("80.00"))
                .items(List.of(
                        OrderItemDto.builder()
                                .productId(PRODUCT_ID_1)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItemDto.builder()
                                .productId(PRODUCT_ID_2)
                                .quantity(3)
                                .price(new BigDecimal("10.00"))
                                .subTotal(new BigDecimal("30.00"))
                                .build()))
                .build();

        createOrderRequestWrongTotalPrice = CreateOrderRequest.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddressDto.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00")) // wrong total price here
                .items(List.of(
                        OrderItemDto.builder()
                                .productId(PRODUCT_ID_1)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItemDto.builder()
                                .productId(PRODUCT_ID_2)
                                .quantity(3)
                                .price(new BigDecimal("10.00"))
                                .subTotal(new BigDecimal("30.00"))
                                .build()))
                .build();

        createOrderRequestWrongProductPrice = CreateOrderRequest.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddressDto.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("80.00"))
                .items(List.of(
                        OrderItemDto.builder()
                                .productId(PRODUCT_ID_1)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItemDto.builder()
                                .productId(PRODUCT_ID_2)
                                .quantity(3)
                                .price(new BigDecimal("20.00")) // not correct as PRODUCT_ID_2 has a price of 10.00
                                .subTotal(new BigDecimal("60.00"))
                                .build()))
                .build();


        Customer customer = Customer.builder()
                .id(new CustomerId(CUSTOMER_ID))
                .build();
        restaurant = Restaurant.builder()
                .id(new RestaurantId(createOrderRequest.getRestaurantId()))
                .products(
                        List.of(
                                Product.builder()
                                        .id(new ProductId(PRODUCT_ID_1))
                                        .name("product-1")
                                        .price(new Money(new BigDecimal("50.00")))
                                        .build(),
                                Product.builder()
                                        .id(new ProductId(PRODUCT_ID_2))
                                        .name("product-2")
                                        .price(new Money(new BigDecimal("10.00")))
                                        .build()
                        )
                )
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderRequestToOrderEntity(createOrderRequest);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(eq(orderDataMapper.createOrderRequestToRestaurantEntity(createOrderRequest))))
                .thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateValidOrder () {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderRequest);
        log.info("testCreateOrder: createOrderResponse is: " + createOrderResponse);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully!", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderRequestWrongTotalPrice));
        assertEquals("Total price: 250.00 is not equal to Order items total: 80.00!", orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderRequestWrongProductPrice));
        assertEquals("Order item price: 20.00 is not valid for product: " + PRODUCT_ID_2, orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithInactiveRestaurant () {
        Restaurant inactiveRestaurant = Restaurant.builder()
                .id(restaurant.getId())
                .products(restaurant.getProducts())
                .active(restaurant.isActive())
                .active(false)
                .build();
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderRequestToRestaurantEntity(createOrderRequest)))
                .thenReturn(Optional.of(inactiveRestaurant));
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderRequest));
        assertEquals("Restaurant with id " + RESTAURANT_ID + " is currently not active!", orderDomainException.getMessage());
    }
}
