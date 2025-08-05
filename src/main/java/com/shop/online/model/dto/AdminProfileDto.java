package com.shop.online.model.dto;

import com.shop.online.utils.UserEnum;
import lombok.Data;

@Data
public class AdminProfileDto {

    private Integer id;

    private String userName;


    private String email;

    private Integer type;

    private UserEnum.Status status;
}
