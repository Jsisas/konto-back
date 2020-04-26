package com.konto.konto.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.util.JwtUtil;
import com.konto.konto.user.User;
import com.konto.konto.user.UserService;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    @Value("${app.jwt-secret}")
    private String jwtSecret;


    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User dbUser = userService.getUserByEmail(user.getEmail());
        JWSObject jwt = getJwtObject(dbUser);

        if (JwtUtil.verifyJwtToken(jwt, jwtSecret) && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return new UsernamePasswordAuthenticationToken(dbUser, jwt, authentication.getAuthorities());
        }
        throw new BadCredentialsException("Password or token is not valid");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    private JWSObject getJwtObject(User dbUser){
        try {
            String payload = objectMapper.writeValueAsString(dbUser);
            return JwtUtil.getJwsObject(payload, jwtSecret);
        } catch (JsonProcessingException e) {
            log.error("Could not write dbUser as json");
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }
}
