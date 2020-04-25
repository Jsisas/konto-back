package com.konto.konto.auth.jwt;

import com.konto.konto.jwt.JwtUtil;
import com.konto.konto.user.User;
import com.konto.konto.user.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider implements AuthenticationProvider {

    private final UserService userService;
    @Value("${app.jwt-secret}")
    private String jwtSecret;


    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User dbUser = userService.getUserByEmail(user.getEmail());
        JWSObject jwt = (JWSObject) authentication.getCredentials();

        if (JwtUtil.verifyJwtToken(jwt, jwtSecret) && user.getPassword().equals(dbUser.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, jwt, authentication.getAuthorities());
        }
        throw new BadCredentialsException("Password or token is not valid");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
