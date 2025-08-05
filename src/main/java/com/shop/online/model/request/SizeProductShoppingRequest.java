package com.shop.online.model.request;

import lombok.Data;

@Data
public class SizeProductShoppingRequest {

    private Integer id;

    private String size;

    private Long quantity;
}
