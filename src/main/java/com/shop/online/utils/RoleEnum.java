package com.shop.online.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

public class RoleEnum {

    @Getter
    public enum Role {
        USER("USER","USER"),
        SUB_ADMIN("SUB_ADMIN","SUB_ADMIN"),
        ADMIN("ADMIN","ADMIN");
        private final String code;
        private final String display;

        Role(String code, String display) {
            this.code = code;
            this.display = display;
        }

    }

    @Getter
    @AllArgsConstructor
    public enum ContentType {
        VIDEO("VIDEO"),
        TEXT("TEXT"),
        QUIZ("QUIZ")
        ;
        private final String code;
    }

    @Getter
    public enum QuestionType {
        SINGLE,
        MULTI,

    }
}
