package com.konto.konto.OpenBankingApi.providers.lhv;

import com.konto.konto.OpenBankingApi.OpenBankingProviders;
import com.konto.konto.OpenBankingApi.OpenBankingRestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    private final OpenBankingRestService openBankingRestService;

    @ResponseBody
    @GetMapping("/auth/redirect")
    public ResponseEntity<Mono<String>> auth(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "state") OpenBankingProviders provider) {



        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", openBankingRestService.CLIENT_ID);
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", "http://localhost:8080/api/openbanking/lhv/auth/redirect&state=LHV");

        Mono<String> result = openBankingRestService.getOpenBankingRestService()
                .method(HttpMethod.POST)
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body( BodyInserters.fromFormData(formData))
                .accept( MediaType.APPLICATION_JSON )
                .retrieve()
                .bodyToMono(String.class);

        return ResponseEntity.ok(result);
    }

    @ResponseBody
    @GetMapping("/auth/redirectFinish")
    public ResponseEntity<Map<String, Object>> auth(@RequestParam(value = "code") String code) {
        return ResponseEntity.ok(Map.of("ok", code));
    }

    @ResponseBody
    @GetMapping("/auth/check")
    public ResponseEntity<Map<String, Object>> auth() {
        return ResponseEntity.ok(Map.of("STATUS", "OK"));
    }
}
