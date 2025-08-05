package com.shop.online.service;

import com.shop.online.entity.User;
import com.shop.online.exception.AuthenticationException;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.NotFoundException;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.request.LoginRequest;
import com.shop.online.repository.UserRepository;
import com.shop.online.security.AccessToken;
import com.shop.online.security.BaseUserDetailsService;
import com.shop.online.security.JwtProvider;
import com.shop.online.security.UserPrincipal;
import com.shop.online.utils.UserEnum;
import com.shop.online.utils.constants.APIConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final BaseUserDetailsService userDetailService;
    private final UserRepository userRepository;

    public AccessToken signinAdmin(LoginRequest loginRequest) throws NotFoundException, AuthenticationException {
        User user = this.userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new ServiceApiException(MessageCode.ERRORS_AUTH_NOT_FOUND.getCode(), MessageCode.ERRORS_AUTH_NOT_FOUND.getDisplay()));
        if (user.getStatus().equals(UserEnum.Status.PENDING) || user.getStatus().equals(UserEnum.Status.BLOCKED)) {
            throw new ServiceApiException(MessageCode.ERRORS_AUTH_USER_04.getCode(), MessageCode.ERRORS_AUTH_USER_04.getDisplay());
        } else if (user.getStatus().equals(UserEnum.Status.DELETED)) {
            throw new NotFoundException(NotFoundException.ERROR_USER_NOT_FOUND, APIConstants.NOT_FOUND_MESSAGE.replace(APIConstants.REPLACE_CHAR, APIConstants.USER));
        } else if (!this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException(AuthenticationException.UNAUTHORIZED_INVALID_PASSWORD,
                    APIConstants.PASSWORD_INPUT_IS_INVALID, false);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(new UserPrincipal().create(user, this.userDetailService.getAuthorities(user)), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.createAccessToken(authentication, true, true);
    }
}
