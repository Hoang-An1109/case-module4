package com.codegym.shopyy.service.impl;

import com.codegym.shopyy.security.JwtTokenProvider;
import com.codegym.shopyy.service.ISecurityService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityServiceImpl implements ISecurityService {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityServiceImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @Override
    public boolean isValidToken(String authToken) {
        String jwt = jwtTokenProvider.getJwtFromBearerToken(authToken);
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(authToken)) {
            return true;
        }
        return false;
    }
}
