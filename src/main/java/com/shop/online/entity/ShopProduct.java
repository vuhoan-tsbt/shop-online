package com.shop.online.entity;

import com.shop.online.utils.enums.ProductEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
public class ShopProduct extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category shopCategory;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private Float price;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private ProductEnum.StatusShopping status;
}
