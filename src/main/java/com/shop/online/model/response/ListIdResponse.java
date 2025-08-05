package com.shop.online.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
public class ListIdResponse {
    private List<Integer> idsInsert;

    private List<Integer> idsUpdate;
}
