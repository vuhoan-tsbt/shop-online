package com.shop.online.security;

import com.shop.online.entity.User;
import com.shop.online.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findById(Integer.valueOf(userName))
                .orElseThrow(()
                        -> new UsernameNotFoundException("Email " + userName + " not found"));

        UserDetails userDetails = null;
        try {
            userDetails = UserPrincipal.create(user, getAuthorities(user));
        } catch (Exception e) {
            log.info("loadUserByUsername error message: " + e.getMessage());
        }
        return userDetails;

    }

    public static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String userRoles = user.getRole().getName();
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
