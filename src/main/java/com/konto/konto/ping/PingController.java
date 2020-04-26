package com.konto.konto.ping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.openBankingApi.providers.lhv.LhvOpenBankingService;
import com.nimbusds.jose.JWSObject;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/ping")
public class PingController {

    private final LhvOpenBankingService lhvOpenBankingService;
    private final ObjectMapper objectMapper;

    @ResponseBody
    @GetMapping("/authenticated")
    public ResponseEntity<String> lhvAuth(HttpServletRequest request) {

        JWSObject jws = (JWSObject) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        return ResponseEntity.ok("ok");
    }
}
