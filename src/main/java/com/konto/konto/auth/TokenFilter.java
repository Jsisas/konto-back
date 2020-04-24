package com.konto.konto.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class TokenFilter extends AbstractAuthenticationProcessingFilter {


    public TokenFilter(String protectedUrls, AuthenticationManager authManager) {
        super(protectedUrls);
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            String token = request.getHeader(AUTHORIZATION);
            token = StringUtils.removeStart(token, "Bearer").trim();
            Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
            return getAuthenticationManager().authenticate(requestAuthentication);
    }
}
