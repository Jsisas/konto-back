package com.konto.konto.OpenBankingApi.lhv;

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

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    private final LhvOpenBankingService lhvOpenBankingService;

    @ResponseBody
    @GetMapping("/auth/redirect")
    public ResponseEntity<String> lhvAuth(HttpServletRequest request, @RequestParam(value = "code") String code) {
        String requestUrl = request.getRequestURL().toString();
        return lhvOpenBankingService.authenticate(code, requestUrl);
    }
}
