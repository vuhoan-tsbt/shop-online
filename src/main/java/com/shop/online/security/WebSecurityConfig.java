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
            "/**/admin/auth/**",
    };
    protected static final String[] AUTH_FOR_POST_METHOD = {
            "/**/user/authentication/sign-in",
            "/**/user/authentication/access-token",
            "/**/user/authentication/register-sponsor",
            "/**/user/authentication/user/sign-in",
            "/**/user/authentication/login-sponsor",
            "/**/user/authentication/web3auth/*",
            "/**/user/authentication/sync",
            "/**/user/authentication/forgot-password",
            "/**/user/authentication/change-password/{resetToken}",
            "/**/user/authentication/verify-token/{resetToken}",
            "/**/images/pre-signed",
            "/**/product/product-attributes",
            "/**/webhook/payment",
            "/**/user/authentication/register",
            "/**/user/authentication/login_user_email",
    };

    protected static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/**/swagger-ui.html",
            "/api/v2/docs",
            // Actuator
            "/actuator/**"
    };

    protected static final String[] AUTH_FOR_PUT_METHOD = {
            "/**/admin/product",
            "/**/api/v1/user/edit/{id}",
    };


    protected static final String[] AUTH_FOR_LOGIN_METHOD = {
            "/**/admin/auth/**",
            "/**/api/v1/admin/history/**",
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
