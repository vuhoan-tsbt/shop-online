package com.shop.online.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserOrderShoppingRequest {
    private Integer userId;
    private String email;
    private String userName;
    private String district;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private String description;
    List<UserProductOrderRequest> orderRequests;


    //  Thêm traceId (UUID) để tracking qua Kafka
    private String traceId;
    private String orderId;   // UUID duy nhất

    //  (Optional) thêm thời gian request để log/debug
    private LocalDateTime requestTime;
}
