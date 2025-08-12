package com.shop.online.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListProductOrderDetailDto {

    private Integer productId;
    private String productName;
    private String image;
    private String size;
    private BigDecimal productPrice;
    private Integer quantity;

    public ListProductOrderDetailDto(Integer productId, String productName, String size,
                                     BigDecimal productPrice, Integer quantity ) {
        this.productId = productId;
        this.productName = productName;
        this.size = size;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }
}
