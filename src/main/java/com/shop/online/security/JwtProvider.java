package com.shop.online.security;

import com.shop.online.exception.BadRequestException;
import com.shop.online.exception.StudentException;
import com.shop.online.utils.Constants;
import com.shop.online.utils.TokenEnum;
import com.shop.online.utils.constants.APIConstants;
import io.jsonwebtoken.*;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
@Slf4j
public class JwtProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;
    public boolean validateJwtToken(String authToken) throws ServletException {
        try {
            if (isTokenExpired(authToken)) {
                throw new ServletException(StudentException.ERROR_JWT_TOKEN_EXPIRED);
            }
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new ServletException(StudentException.ERROR_JWT_INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new ServletException(StudentException.ERROR_JWT_TOKEN_EXPIRED);
        }
    }

    /**
     * Creates access token.
     *
     * @param authentication {@link Authentication}
     * @param rememberMe     if application is remember me
     * @return {@link AccessToken}
     */

    public AccessToken createAccessToken(Authentication authentication, boolean rememberMe, boolean isAdminApi) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String name = String.valueOf(principal.getUserId());
        long now = (new Date()).getTime();
        long dateToMilliseconds = 24 * 60 * 60 * 1000L;
        Date validity;
        Date refreshTokenExpiration = new Date(now + TokenEnum.REFRESH_TOKEN_EXPIRED.getValue() * dateToMilliseconds);
        if (rememberMe) {
            validity = new Date(now + TokenEnum.TOKEN_REMEMBER_ME_EXPIRED.getValue() * dateToMilliseconds);
        } else {
            validity = new Date(now + TokenEnum.TOKEN_JWT_EXPIRED.getValue() * dateToMilliseconds);
        }

        //Build refresh token
        String refreshToken = Jwts.builder().setSubject(name)
                .setExpiration(refreshTokenExpiration)
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();
        AccessToken accessToken = new AccessToken();

        accessToken.setExpried(validity);
        accessToken.setRefreshToken(refreshToken);
        accessToken.setUserId(principal.getUserId());
        accessToken.setTokenType(Constants.JWT_TOKEN_TYPE);
        accessToken.setRoles(principal.getRoles().getName());

        //Build access token
        String jwt = Jwts.builder().setSubject(name)
                .setClaims(buildClaims(principal, isAdminApi))
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();
        accessToken.setToken(jwt);

        return accessToken;
    }

    /**
     * Get subject from input token
     *
     * @param token access token
     * @return subject
     */
    public Integer getSubjectFromToken(String token) {
        return Integer.valueOf(Jwts.parser()
                .setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    /**
     * build Claims
     *
     * @param principal User Principal
     */
    private Map<String, Object> buildClaims(UserPrincipal principal, boolean isAdminApi) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", principal.getUserId());
        claims.put("nameRole", principal.getRoles().getName());
        claims.put(Constants.JWT_CLAIM_KEY_IS_ADMIN_API, isAdminApi);
        return claims;
    }

    /**
     * Get Claim info from access token value.
     *
     * @param token    the access token
     * @param claimKey Claim key
     * @return Claims info for claimKey
     */
    public Object getClaimInfo(String token, String claimKey) throws BadRequestException {
        Claims claims = Jwts.parser().
                setSigningKey(this.jwtSecret).
                parseClaimsJws(token).
                getBody();
        if (claims.get(claimKey) == null) {
            if (!Constants.JWT_CLAIM_KEY_IS_ADMIN_API.equals(claimKey)) {
                throw new BadRequestException(BadRequestException.ERROR_INVALID_TOKEN, APIConstants.TOKEN_INVALID_MSG, false);
            } else {
                return null;
            }
        }
        return claims.get(claimKey);
    }

    /**
     * Is Token Expired
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
