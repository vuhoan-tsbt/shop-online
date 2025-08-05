package com.shop.online.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class APINsbResponseError {

    public long timestamp;
    public String code;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public String error;
    public Object message;

}
