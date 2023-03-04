package com.food.ordering.system.order.service.domain.ports.inputs;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderRequest;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

// Will be used by client of this application - e.g Postman
public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderRequest createOrderRequest);
    TrackOrderResponse trackOrder(@Valid TrackOrderRequest trackOrderRequest);
}
