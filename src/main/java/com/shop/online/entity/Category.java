package com.shop.online.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "category")
@Entity
public class Category extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String name;

    private String headerUrl;

    @Column(columnDefinition = "text")
    private String description;

    private Boolean deleteFlg = false;

}
