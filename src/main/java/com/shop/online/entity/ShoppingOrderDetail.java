package com.shop.online.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shopping_order_detail")
public class ShoppingOrderDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_product_id")
    private ShopProduct shopProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_order_id")
    private ShopOrder shopOrder;

    @Column(name = "product_name_ship")
    private String productNameShip;

    @Column(name = "product_size_ship")
    private String productSizeShip;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_price")
    private BigDecimal productPrice;

}
