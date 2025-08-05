package com.shop.online.service;

import com.shop.online.repository.UserRepository;
import com.shop.online.security.UserPrincipal;

import com.shop.online.utils.constants.PageableConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
@Slf4j

public class BaseService {


    @Autowired
    private  UserRepository userRepository;



//    public Optional<User> getUserByEmail(String email) {
//        Optional<User> user = this.userRepository.findFirstByEmail(email);
//        if (user.isPresent()) {
//            if (user.get().getRole().getName().equals(RoleEnum.Role.ADMIN.getCode())) {
//                return user;
//            } else if (user.get().getRole().getName().equals(RoleEnum.Role.SUPPORT.getCode())) {
//                return user;
//            } else if (user.get().getRole().getName().equals(RoleEnum.Role.TEACHER.getCode())) {
//                return user;
//            }
//        }
//        return Optional.empty();
//    }

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

}
