package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.domain.ports.inputs.PaymentResponseMessageListener;

public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    @Override
    public void paymentCompleted (PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled (PaymentResponse paymentResponse) {

    }
}
