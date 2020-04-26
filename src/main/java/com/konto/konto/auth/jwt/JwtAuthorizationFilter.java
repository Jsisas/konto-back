package com.konto.konto.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.user.UserService;
import com.konto.konto.util.JwtUtil;
import com.konto.konto.user.User;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_COOKIE_NAME = "token";

    public JwtAuthorizationFilter(AuthenticationManager authManager, UserService userService) {
        super(authManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        boolean isTokenInCookie = hasCookieToken(req);
        boolean isTokenInAuthHeader = hasAuthHeaderToken(req);
        String token = "";
        if (isTokenInCookie) {
            Cookie tokenCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(AUTH_COOKIE_NAME))
                    .findFirst()
                    .orElse(null);
            token = tokenCookie.getValue();
        }else if (isTokenInAuthHeader) {
            token = extractTokenFromBearerHeader(req.getHeader(AUTH_HEADER_NAME));
        } else {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            JWSObject jwt = JwtUtil.parseToken(token);
            String payload = jwt.getPayload().toString();
            User user = new ObjectMapper().readValue(payload, User.class);
            if (user != null && user.getId() != null) {
                User dbUser = userService.getUserById(user.getId());
                return new UsernamePasswordAuthenticationToken(dbUser, jwt, new ArrayList<>());
            }
            return null;
        } catch (JsonProcessingException e) {
            log.error("Invalid token payload format: {}", e.getMessage());
        }
        return null;
    }

    private boolean hasCookieToken(HttpServletRequest req) {
        if (req.getCookies() == null || req.getCookies().length < 1) {
            return false;
        }

        Cookie tokenCookie = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(AUTH_COOKIE_NAME))
                .findFirst()
                .orElse(null);

        return tokenCookie != null &&
                tokenCookie.getValue() != null &&
                !tokenCookie.getValue().isEmpty();
    }

    private boolean hasAuthHeaderToken(HttpServletRequest req) {
        return req.getHeader(AUTH_HEADER_NAME) != null && !req.getHeader(AUTH_HEADER_NAME).isEmpty();
    }

    private String extractTokenFromBearerHeader(String authHeader){
        return authHeader.substring(7, authHeader.length());
    }
}