package com.shop.online.exception;


import lombok.Getter;

@Getter
public enum MessageCode implements CodeEnum {

    ERRORS_COMMON_001("ERRORS_COMMON_001", "Khoá học không tồn tại"),
    MSG_ERR_USER_AUTH_002("MSG_ERR_USER_AUTH_002", "ユーザーが存在しません。"),
    ERRORS_AUTH_USER_04("ERRORS_AUTH_USER_04", "Tài Khoản của bạn đã bị xóa"),
    ERRORS_AUTH_NOT_FOUND("ERRORS_AUTH_NOT_FOUND", "Tài Khoản của bạn không tồn tại"),
    ERRORS_NAME_COURSE("ERRORS_NAME_COURSE", "Khóa học đã tồn tại, vui lòng chọn tên khác");
    private final String code;
    private final String display;

    MessageCode(String code, String display) {
        this.code = code;
        this.display = display;
    }
}
