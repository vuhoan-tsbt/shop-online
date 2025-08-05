package com.shop.online.utils;

import com.shop.online.model.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

public class PageUtils {

    private static final Logger log = LoggerFactory.getLogger(PageUtils.class);

    /**
     * Create paging with basic information
     *
     * @param <T>
     * @param page {@link Page} of T
     * @return {@link PageInfo}
     */
    public static <T> PageInfo<T> pagingResponse(Page<T> page) {
        // page info
        PageInfo<T> pageInfo = new PageInfo<T>();
        pageInfo.setTotal(page.getTotalElements());
        pageInfo.setLimit(page.getSize());
        pageInfo.setPages(page.getTotalPages());
        pageInfo.setPage(page.getNumber() + 1);
        pageInfo.setResult(page.getContent());
        return pageInfo;
    }
}
