package com.shop.online.model.dto;


import com.shop.online.utils.enums.ProductEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class HistoryOrderUserDto {

    private Integer orderId;
    private BigDecimal totalMoney;
    private String createdAt;
    private ProductEnum.ShopOrderStatus status;
    private Integer menuNumberByStatus;

    public HistoryOrderUserDto(Integer orderId, BigDecimal totalMoney, LocalDateTime createdAt,
                               ProductEnum.ShopOrderStatus status) {
        this.orderId = orderId;
        this.totalMoney = totalMoney;
        this.createdAt = createdAt.toInstant(ZoneOffset.UTC).toString();
        this.status = status;

    }
}
