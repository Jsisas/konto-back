package com.konto.konto.OpenBankingApi.providers.lhv;

import com.konto.konto.OpenBankingApi.OpenBankingProviders;
import com.konto.konto.OpenBankingApi.OpenBankingRestService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    private final RestTemplate lhvRestTemplate;

    @ResponseBody
    @GetMapping("/auth/redirect")
    public ResponseEntity<String> auth(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "state") String provider) {

        if(provider.equals("UNFINISHED")){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", "Bearer liismarimannik");

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("client_id", OpenBankingRestService.CLIENT_ID);
            formData.add("code", code);
            formData.add("grant_type", "authorization_code");
            formData.add("redirect_uri", "https://localhost:8443/api/openbanking/lhv/auth/redirect");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(formData, headers);
            return lhvRestTemplate.postForEntity( "/oauth/token", request , String.class );
        }
        return ResponseEntity.ok(provider);
    }

    @ResponseBody
    @GetMapping("/auth/check")
    public ResponseEntity<Map<String, Object>> auth() {
        return ResponseEntity.ok(Map.of("STATUS", "OK"));
    }
}
