package com.konto.konto.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.jwt.JwtUtil;
import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.konto.konto.user.User;
import com.nimbusds.jose.JWSObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        if (req.getCookies() == null || req.getCookies().length < 1) {
            chain.doFilter(req, res);
            return;
        }

        Cookie tokenCookie = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .orElse(null);

        if (tokenCookie == null) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenCookie);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Cookie tokenCookie) {
        JWSObject token = JwtUtil.parseToken(tokenCookie.getValue());
        String tokenPayload = JwtUtil.parseToken(tokenCookie.getValue()).getPayload().toString();
        try {
            User authObj = new ObjectMapper().readValue(tokenPayload, User.class);
            if (JwtUtil.verifyJwtToken(token, JwtUtil.jwtSecret) && authObj != null) {
                return new UsernamePasswordAuthenticationToken(authObj, authObj, new ArrayList<>());
            }
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }
}
