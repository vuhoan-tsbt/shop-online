package com.shop.online.exception;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class ServiceApiException extends RuntimeException {
    private static final long serialVersionUID = -4998452844437648420L;

    private final String code;

    private final String message;

    public ServiceApiException(MessageCode messageCode) {
        code = messageCode.getCode();
        message = messageCode.getDisplay();
    }

}
