package com.shop.online.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName("error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder(value = {"code", "message", "details",})
public class ApplicationErrorResponseDto {

    private static final String KEY_CODE = "code";
    private static final String KEY_DETAIL = "details";
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "message";

    @JsonProperty(KEY_CODE)
    private String code;

    @JsonProperty(KEY_MESSAGE)
    private String message;

    @JsonProperty(KEY_DETAIL)
    @Setter(value = AccessLevel.NONE)
    private final List<Map<String, Object>> details = new ArrayList<>();


    public ApplicationErrorResponseDto(String code,  String message) {
        this.code = code;
        this.message = message;
    }

    public void addErrorKeyAndObjectDetail(String key,  Object value) {
        details.add(Map.of(
                key, value
        ));
    }
}
