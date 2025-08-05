package com.shop.online.security;

import com.shop.online.entity.Role;
import com.shop.online.entity.User;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.utils.UserEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * User details.
 */
@Getter
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Integer userId;
    private String name;
    @JsonIgnore
    private String password;
    private Role roles;
    private UserEnum.Status status;
    private Collection<? extends GrantedAuthority> authorities;


    public UserPrincipal(Integer id, String password,
                         UserEnum.Status status, Role roles, Collection<? extends GrantedAuthority> authorities) {
        this.userId = id;
        this.password = password;
        this.status = status;
        this.roles = roles;
        this.authorities = authorities;


    }

    public static UserPrincipal create(User user, Collection<? extends GrantedAuthority> authorities) {
        if (Objects.isNull(user)) {
            throw new ServiceApiException(MessageCode.MSG_ERR_USER_AUTH_002);
        }

        return new UserPrincipal(user.getId(), user.getPassword(), user.getStatus(), user.getRole(), authorities);
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return UserEnum.Status.ACTIVE.equals(status);
    }
}
