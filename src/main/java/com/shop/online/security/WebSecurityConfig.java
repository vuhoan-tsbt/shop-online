package com.shop.online.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final BaseUserDetailsService userDetailService;

    private final JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/v2/api-docs", config);
        return new CorsFilter(source);
    }

    protected static final String[] AUTH_FOR_GET_METHOD = {
            "/{path1}/admin/auth/**",
            "/{path1}/user/auth/**",
    };

    protected static final String[] AUTH_FOR_POST_METHOD = {
            "/{path1}/user/authentication/sign-in",
            "/{path1}/user/authentication/access-token",
            "/{path1}/user/authentication/user/sign-in",
            "/{path1}/user/authentication/sync",
            "/{path1}/user/authentication/forgot-password",
            "/user/authentication/change-password/{path:.+}", // Match mọi thứ sau change-password
            "/{path1}/user/authentication/verify-token/{path:.+}", // Match mọi thứ sau verify-token
            "/{path1}/webhook/payment",
            "/{path1}/user/auth/register",
    };

    protected static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources",
            "/swagger-resources/**", // Hợp lệ trong PathPatternParser nếu kết thúc bằng /**
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/{path1}/swagger-ui.html",
            "/api/v2/docs",
            // Actuator
            "/actuator/**"
    };

    protected static final String[] AUTH_FOR_PUT_METHOD = {
            "/{path1}/admin/product",
            "/{path1}/api/v1/user/edit/{path:.+}", // match toàn bộ sau edit/
    };

    protected static final String[] AUTH_FOR_LOGIN_METHOD = {
            "/{path1}/admin/auth/**",
            "/{path1}/api/v1/admin/history/{path:.+}",
    };
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.GET, AUTH_FOR_GET_METHOD).permitAll()
                                .requestMatchers(HttpMethod.POST, AUTH_FOR_POST_METHOD).permitAll()
                                .requestMatchers(HttpMethod.PUT, AUTH_FOR_PUT_METHOD).permitAll()
                                .requestMatchers(HttpMethod.POST, AUTH_FOR_LOGIN_METHOD).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll().anyRequest().permitAll())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
