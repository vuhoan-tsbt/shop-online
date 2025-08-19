package com.shop.online.kafka.consumer;

import com.shop.online.model.request.UserOrderShoppingRequest;
import com.shop.online.service.UserOrderShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final UserOrderShopService userOrderShopService;

    @KafkaListener(
            topics = "order-created",
            groupId = "shopping-order-group"
    )
    public void consume(UserOrderShoppingRequest request, Acknowledgment ack) {
        log.info("Received order request from Kafka: {}", request);

        try {
            userOrderShopService.userOrder(request);
            ack.acknowledge();
            log.info("Order persisted successfully for user: {}", request.getOrderId());
        } catch (Exception e) {
            log.error("Failed to process order: {}", request, e);
        }
    }
}
