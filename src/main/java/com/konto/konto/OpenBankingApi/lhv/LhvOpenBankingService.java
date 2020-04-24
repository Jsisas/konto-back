package com.konto.konto.OpenBankingApi.lhv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LhvOpenBankingService {

    private final RestTemplate lhvRestTemplate;

    public ResponseEntity<String> authenticate(String code, String redirectUrl){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", LhvOpenBankingConfig.CLIENT_ID);
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", redirectUrl);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        return lhvRestTemplate.postForEntity("/oauth/token", request, String.class);
    }

}
