package com.shop.online.model.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UserProductOrderRequest {

    private Integer productId;
    private String productName;
    private Integer quantity;
    private String size;
    private BigDecimal amount;
}
