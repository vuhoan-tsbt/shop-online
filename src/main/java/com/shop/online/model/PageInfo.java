package com.shop.online.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private long total;
    private int limit;
    private int pages;
    private int page;
    private List<T> result;

    public PageInfo() {
    }
}
