package com.shop.online.utils;

import lombok.Getter;

public class UserEnum {

    @Getter
    public enum Status {
        ACTIVE("ACTIVE"),
        WAIT_FOR_CONFIRMATION("WAIT_FOR_CONFIRMATION"),
        PENDING("PENDING"),
        DELETED("DELETED"),
        BLOCKED("BLOCKED");

        private final String code;

        Status(String code) {
            this.code = code;
        }
    }

    @Getter
    public enum StatusCourse {
        ACTIVE("ACTIVE"),
        PENDING("PENDING"),
        PROGRESS("PROGRESS");

        private final String code;

        StatusCourse(String code) {
            this.code = code;
        }
    }
}
