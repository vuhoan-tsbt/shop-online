package com.shop.online.cache;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiCacheInterceptor implements HandlerInterceptor {

    private final Cache cache;

    public ApiCacheInterceptor(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("apiCache");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String key = request.getRequestURI() + "?" + request.getQueryString();
        String cachedResponse = cache.get(key, String.class);

        if (cachedResponse != null) {
            try {
                response.setContentType("application/json");
                response.getWriter().write(cachedResponse);
                return false; // chặn không cho vào Controller nữa
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
