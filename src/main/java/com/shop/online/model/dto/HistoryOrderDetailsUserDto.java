package com.shop.online.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class HistoryOrderDetailsUserDto {

    private HistoryOrderUserDto  historyOrderUserDto;

    List<ListProductOrderDetailDto> listProductOrderDetailDtoList;
}
