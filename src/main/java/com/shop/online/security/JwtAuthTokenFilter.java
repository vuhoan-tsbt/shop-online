package com.shop.online.security;

import com.shop.online.exception.StudentException;
import com.shop.online.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        UserDetails userDetails;
        try {
            String jwt = getJwtToken(request);
            if (jwt != null && this.tokenProvider.validateJwtToken(jwt)) {
                String userId = String.valueOf(this.tokenProvider.getClaimInfo(jwt, "userId"));
                Boolean isAdminApi = (Boolean) this.tokenProvider.getClaimInfo(jwt, Constants.JWT_CLAIM_KEY_IS_ADMIN_API);
                //Prevent user jwt login to admin API
                if (null != isAdminApi && request.getRequestURI().contains(Constants.API_ADMIN_URL_REGEX) && Boolean.TRUE.equals(!isAdminApi)) {
                    this.buildExceptionOutput(request, response, HttpStatus.UNAUTHORIZED.name(), "Unauthorized",
                            HttpStatus.UNAUTHORIZED.value(), HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                userDetails = this.userDetailsService.loadUserByUsername(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            if (StringUtils.isNotEmpty(e.getMessage()) && e.getMessage().contains(Constants.JWT_ERROR_REGEX)) {
                this.buildExceptionOutput(request, response, e.getMessage(), e.getMessage(),
                        HttpStatus.UNAUTHORIZED.value(), HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            this.buildExceptionOutput(request, response, StudentException.ERROR_JWT_INVALID_TOKEN, StudentException.ERROR_JWT_INVALID_TOKEN,
                    HttpStatus.UNAUTHORIZED.value(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setBufferSize(64 * 1024);

        filterChain.doFilter(request, response);
    }


    public static String getJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(Constants.JWT_TOKEN_TYPE)) {
            return authHeader.replace(Constants.JWT_TOKEN_TYPE, "");
        }
        return null;
    }

    public void buildExceptionOutput(HttpServletRequest request, HttpServletResponse response,
                                     String errorMsg, String msg, Integer status, Integer responseStatus) throws IOException {
        String sb = "{ " +
                "\"path\": \"" + request.getRequestURL() + "\"" + "," +
                "\"error\":" + "\"" + errorMsg + "\"" + "," +
                "\"message\":" + "\"" + msg + "\"" + "," +
                "\"timestamp\":" + new Date().getTime() + "," +
                "\"status\":" + status +
                "} ";
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
        response.setStatus(responseStatus);
        response.getWriter().write(sb);
    }


}
