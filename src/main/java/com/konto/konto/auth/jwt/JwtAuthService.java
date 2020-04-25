package com.konto.konto.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.jwt.JwtUtil;
import com.konto.konto.user.User;
import com.konto.konto.userToken.UserTokenService;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    private final JwtAuthProvider jwtAuthProvider;
    private final UserTokenService userTokenService;
    private final ObjectMapper objectMapper;

    public void authenticate(User user){
        user.setTokens(userTokenService.getByUserId(user.getId()));

        try {
            String payload = objectMapper.writeValueAsString(user);
            JWSObject jwsObject = JwtUtil.getJwsObject(payload, jwtSecret);

            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
            Authentication authenticationToken = jwtAuthProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(user, jwsObject, authorities));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
