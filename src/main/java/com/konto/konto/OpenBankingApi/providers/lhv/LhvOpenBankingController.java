package com.konto.konto.OpenBankingApi.providers.lhv;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    private final RestTemplate lhvRestTemplate;

    @ResponseBody
    @GetMapping("/auth/redirect")
    public ResponseEntity<String> lhvAuth(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "state") String provider) {

        if(provider.equals("UNFINISHED")){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "Bearer liismarimannik");

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("client_id", LhvOpenBankingConfig.CLIENT_ID);
            formData.add("code", code);
            formData.add("grant_type", "authorization_code");
            formData.add("redirect_uri", "https://localhost:8443/api/openbanking/lhv/auth/redirect");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(formData, headers);
            return lhvRestTemplate.postForEntity( "/oauth/token", request , String.class );
        }

        return ResponseEntity.ok(provider);
    }
}
