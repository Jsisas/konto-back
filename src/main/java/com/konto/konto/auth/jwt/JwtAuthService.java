package com.konto.konto.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.user.User;
import com.konto.konto.user.UserService;
import com.konto.konto.userToken.UserTokenService;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    private final JwtAuthProvider jwtAuthProvider;
    private final UserTokenService userTokenService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public User authenticate(User user, HttpServletResponse res) {
        if (!validateUserLogin(user)) {
            throw new DataIntegrityViolationException("Missing fields some fields while logging in");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
        Authentication authenticationToken = jwtAuthProvider
                .authenticate(new UsernamePasswordAuthenticationToken(user, null, authorities));

        String jwtToken = ((JWSObject) authenticationToken.getCredentials()).serialize();
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        res.addCookie(new Cookie("token", jwtToken));
        return (User) authenticationToken.getPrincipal();
    }

    private boolean validateUserLogin(User user) {
        return (
                user.getEmail() != null &&
                        !user.getEmail().isEmpty() &&
                        user.getPassword() != null &&
                        !user.getPassword().isEmpty());
    }

}
