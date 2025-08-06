package com.shop.online.service;

import com.shop.online.repository.UserRepository;
import com.shop.online.security.UserPrincipal;

import com.shop.online.utils.Constants;
import com.shop.online.utils.constants.PageableConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@Slf4j

public class BaseService {

    @Value("${app.s3.aws.cloudfront-image}")
    protected String cloudFrontImage;

    @Autowired
    private UserRepository userRepository;

    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = null;
        if (principal instanceof UserDetails) {
            userId = ((UserPrincipal) principal).getUserId();
        } else if (!principal.equals("anonymousUser")) {
            userId = Integer.parseInt((String) principal);
        }
        return userId;
    }

    public PageRequest buildPageRequest(Integer page, Integer limit, Sort sort) {
        page = page == null ? PageableConstants.DEFAULT_PAGE : page - PageableConstants.DEFAULT_PAGE_INIT;
        limit = limit == null ? PageableConstants.DEFAULT_SIZE : limit;
        return PageRequest.of(page, limit, sort);
    }

    public PageRequest buildPageRequest(Integer page, Integer limit) {
        page = page == null ? PageableConstants.DEFAULT_PAGE : page - PageableConstants.DEFAULT_PAGE_INIT;
        limit = limit == null ? PageableConstants.DEFAULT_SIZE : limit;
        return PageRequest.of(page, limit);
    }

    public String convertAwsUrlToUri(String s3Url) {
        if (StringUtils.isNotEmpty(s3Url)) {
            if (s3Url.contains(Constants.HTTPS_STRING + this.cloudFrontImage + Constants.SLASH)) {
                s3Url = s3Url.replace(Constants.HTTPS_STRING + this.cloudFrontImage + Constants.SLASH, StringUtils.EMPTY);
            } else if (s3Url.contains(Constants.HTTP_STRING + this.cloudFrontImage + Constants.SLASH)) {
                s3Url = s3Url.replace(Constants.HTTP_STRING + this.cloudFrontImage + Constants.SLASH, StringUtils.EMPTY);
            }
        }
        return s3Url;
    }

    public String convertKeyword(String keyword) {
        if (keyword.contains("%")) {
            keyword = keyword.replaceAll("%", "\\\\%");
        }
        if (keyword.contains("_")) {
            keyword = keyword.replaceAll("_", "\\\\_");
        }
        keyword = keyword.trim();

        return keyword;
    }

    public Sort buildSortJPA(String sortColumn, String sortType) {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        if (sortType != null && sortColumn != null) {
            switch (sortType) {
                case Constants.SORT_ASC:
                    sort = Sort.by(Sort.Order.asc(sortColumn));
                    break;
                case Constants.SORT_DESC:
                    sort = Sort.by(Sort.Order.desc(sortColumn));
                    break;
            }
        }
        return sort;
    }

    public String buildCloudFrontImageUri(String uri) {
        if (StringUtils.isNotEmpty(uri) && !uri.contains(Constants.HTTPS_STRING)) {
            return Constants.HTTPS_STRING + this.cloudFrontImage + Constants.SLASH + uri;
        } else {
            return uri;
        }
    }

}
