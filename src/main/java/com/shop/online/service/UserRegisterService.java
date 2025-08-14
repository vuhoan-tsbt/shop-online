package com.shop.online.service;

import com.shop.online.entity.Role;
import com.shop.online.entity.User;
import com.shop.online.exception.AuthenticationException;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.NotFoundException;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.request.LoginRequest;
import com.shop.online.model.response.IdResponse;
import com.shop.online.repository.RoleRepository;
import com.shop.online.repository.UserRepository;
import com.shop.online.security.AccessToken;
import com.shop.online.security.BaseUserDetailsService;
import com.shop.online.security.JwtProvider;
import com.shop.online.security.UserPrincipal;
import com.shop.online.utils.Constants;
import com.shop.online.utils.RoleEnum;
import com.shop.online.utils.UserEnum;
import com.shop.online.utils.constants.APIConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final BaseUserDetailsService userDetailService;

    @Transactional(rollbackFor = Exception.class)
    public IdResponse userRegister(String email, String password) {
        String passwordHash = passwordEncoder.encode(password);
        Role role = roleRepository.findByName(RoleEnum.Role.USER.name()).orElseThrow(()
                -> new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordHash);
        user.setStatus(UserEnum.Status.ACTIVE);
        user.setType(2);
        user.setRole(role);
        userRepository.save(user);
        return new IdResponse(user.getId());
    }

    public AccessToken userLogin(LoginRequest loginRequest) throws  AuthenticationException {
        User user = this.userRepository.findByEmailAndType(loginRequest.getEmail(), Constants.TYPE_USER).orElseThrow(() ->
                new ServiceApiException(MessageCode.ERRORS_AUTH_NOT_FOUND.getCode(), MessageCode.ERRORS_AUTH_NOT_FOUND.getDisplay()));
        if (user.getStatus().equals(UserEnum.Status.BLOCKED)) {
            throw new ServiceApiException(MessageCode.ERRORS_AUTH_USER_04.getCode(), MessageCode.ERRORS_AUTH_USER_04.getDisplay());
        } else if (!this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException(AuthenticationException.UNAUTHORIZED_INVALID_PASSWORD,
                    APIConstants.PASSWORD_INPUT_IS_INVALID, false);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(new UserPrincipal().create(user, this.userDetailService.getAuthorities(user)), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.createAccessToken(authentication, true, true);
    }
}
