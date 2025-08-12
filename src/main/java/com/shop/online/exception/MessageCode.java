package com.shop.online.exception;


import lombok.Getter;

@Getter
public enum MessageCode implements CodeEnum {

    ERRORS_COMMON_001("ERRORS_COMMON_001", "Khoá học không tồn tại"),
    ERRORS_AUTH_USER_02("ERRORS_AUTH_USER_02", "Email address not found."),
    MSG_ERR_USER_AUTH_002("MSG_ERR_USER_AUTH_002", "ユーザーが存在しません。"),
    ERRORS_AUTH_USER_04("ERRORS_AUTH_USER_04", "Tài Khoản của bạn đã bị xóa"),
    ERRORS_AUTH_NOT_FOUND("ERRORS_AUTH_NOT_FOUND", "Tài Khoản của bạn không tồn tại"),
    ERRORS_NAME_COURSE("ERRORS_NAME_COURSE", "Khóa học đã tồn tại, vui lòng chọn tên khác"),
    ERRORS_NAME_PRODUCT_SHOPPING("ERRORS_NAME_PRODUCT_SHOPPING", "The product name already exists."),
    ERRORS_CATEGORY_SHOP_03("ERRORS_CATEGORY_SHOP_03", "The number of characters exceeds the input limit."),
    ERRORS_CATEGORY_NAME_EXIST("ERRORS_CATEGORY_NAME_EXIST", "This name already exists."),
    ERRORS_CATEGORY_DELETE("ERRORS_CATEGORY_DELETE", "Cannot delete a category that is in use."),
    ERRORS_NAME_UPDATE_PRODUCT_SHOPPING("ERRORS_NAME_UPDATE_PRODUCT_SHOPPING",
            "You cannot change the name to one that is the same as a previously created product name."),
    ERRORS_UPDATE_INVENTORY_PRODUCT_SHOPPING("ERRORS_UPDATE_INVENTORY_PRODUCT_SHOPPING",
            "Cannot reduce the quantity of the product."),
    ERRORS_NAME_AND_SIZE_PRODUCT_SHOPPING("ERRORS_NAME_AND_SIZE_PRODUCT_SHOPPING", "Duplicate product name and size."),
    ERRORS_SHOPPING_ORDER_01("ERRORS_SHOPPING_ORDER_01", "This size is out of stock. Please try a different size.");
    private final String code;
    private final String display;

    MessageCode(String code, String display) {
        this.code = code;
        this.display = display;
    }
}
