package com.konto.konto.openBankingApi.lhv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.jwt.JwtUtil;
import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.konto.konto.auth.lhv.LhvAuthProvider;
import com.nimbusds.jose.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LhvOpenBankingService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final RestTemplate lhvRestTemplate;
    private final LhvAuthProvider lhvAuthProvider;
    private final ObjectMapper objectMapper;

    public ResponseEntity<OpenBankingAuth> authenticate(String code, String redirectUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", LhvOpenBankingConfig.CLIENT_ID);
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", redirectUrl);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<OpenBankingAuth> authResponse = lhvRestTemplate
                .postForEntity("/oauth/token", request, OpenBankingAuth.class);

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
