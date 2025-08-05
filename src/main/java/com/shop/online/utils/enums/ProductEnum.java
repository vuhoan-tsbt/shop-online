package com.shop.online.utils.enums;

import com.shop.online.exception.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProductEnum {

    @Getter
    @AllArgsConstructor
    public enum Status implements CodeEnum {
        NEW("NEW", "NEW"),
        SALE("SALE", "SALE"),
        SOLD("SOLD", "SOLD"),
        PENDING("PENDING", "PENDING"),
        FAIL("FAIL", "FAIL"),
        DELETED("DELETED", "DELETED"),
        REJECTED("REJECTED", "REJECTED");

        private final String code;
        private final String display;
    }

    @Getter
    @AllArgsConstructor
    public enum StatusShopping {
        ACTIVE("ACTIVE"),
        DELETED("DELETED");

        private final String display;

    }
}
