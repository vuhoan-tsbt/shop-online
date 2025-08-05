package com.shop.online.utils;

public enum TemplateApiErrorEnum implements ErrorCodes {


    ISO_DATE_FORMAT_CHECK("COMMON-FM00001.id", "COMMON-FM00001.message");

    TemplateApiErrorEnum(String errIdCode, String messageCode) {
        this.errIdCode = errIdCode;
        this.messageCode = messageCode;
    }

    /**
     *
     */
    private final String errIdCode;

    private final String messageCode;

    @Override
    public String getErrIdCode() {
        return this.errIdCode;
    }

    @Override
    public String getMessageCode() {
        return this.messageCode;
    }
}
