package com.shop.online.kafka;

import com.shop.online.model.request.UserOrderShoppingRequest;
import com.shop.online.service.OrderSequenceService;
import com.shop.online.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPublisher {

    private final KafkaTemplate<String, UserOrderShoppingRequest> kafkaTemplate;
    private final SnowflakeIdGenerator idGenerator;
    private final OrderSequenceService orderSequenceService;

    public void publishOrder(UserOrderShoppingRequest event) {
        Integer nextId = orderSequenceService.getNextId();
        String orderId = "SP" + idGenerator.nextId() + "Ur" + event.getUserId() + nextId;
        // Nếu chưa có traceId thì tự generate
        if (event.getTraceId() == null) {
            event.setTraceId(UUID.randomUUID().toString());
        }
        // sinh orderId duy nhất
        if (event.getOrderId() == null) {
            event.setOrderId(orderId);
        }

        log.info("Publishing order to Kafka: {}", event);
        // publish với traceId làm key
        kafkaTemplate.send("order-created", event.getTraceId(), event);
    }
}
