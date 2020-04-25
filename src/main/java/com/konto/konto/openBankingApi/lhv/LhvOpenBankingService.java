package com.konto.konto.openBankingApi.lhv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.jwt.JwtUtil;
import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.konto.konto.auth.lhv.LhvAuthProvider;
import com.konto.konto.openBankingApi.lhv.api.auth.LhvApiAuthService;
import com.nimbusds.jose.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LhvOpenBankingService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final LhvAuthProvider lhvAuthProvider;
    private final ObjectMapper objectMapper;
    private final LhvApiAuthService lhvApiAuthService;

    public ResponseEntity<OpenBankingAuth> authenticate(String code, String redirectUrl){
        ResponseEntity<OpenBankingAuth> authResponse = lhvApiAuthService.authenticate(code, redirectUrl);

        if(authResponse.hasBody() && authResponse.getBody() != null){
            try {
                String payload = objectMapper.writeValueAsString(authResponse.getBody());
                JWSObject jwsObject = JwtUtil.getJwsObject(payload, jwtSecret);

                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
                Authentication authenticationToken = lhvAuthProvider
                        .authenticate(new UsernamePasswordAuthenticationToken(null, jwsObject, authorities));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return authResponse;
    }
}
