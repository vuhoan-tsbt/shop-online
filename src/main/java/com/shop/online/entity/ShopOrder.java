package com.shop.online.entity;

import com.shop.online.utils.enums.ProductEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shopping_order")
public class ShopOrder extends BaseEntity {

    private String email;

    @Column(nullable = false, unique = true, updatable = false)
    private String orderId;

    private String userName;

    @Column(name = "delivery_address", columnDefinition = "TEXT")
    private String deliveryAddress;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private ProductEnum.ShopOrderStatus status;

    private Integer userId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
