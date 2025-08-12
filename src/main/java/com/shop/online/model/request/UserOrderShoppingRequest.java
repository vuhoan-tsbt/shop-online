package com.shop.online.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserOrderShoppingRequest {

    private String email;
    private String userName;
    private String district;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private String description;
    List<UserProductOrderRequest> orderRequests;
}
