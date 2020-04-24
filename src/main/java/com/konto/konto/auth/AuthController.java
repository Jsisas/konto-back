package com.konto.konto.auth;

import com.konto.konto.OpenBankingApi.lhv.LhvOpenBankingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final LhvOpenBankingService lhvOpenBankingService;

    @ResponseBody
    @GetMapping("/lhv")
    public ResponseEntity<String> lhvAuth(HttpServletRequest request, @RequestParam(value = "code") String code) {
        String requestUrl = request.getRequestURL().toString();
        return lhvOpenBankingService.authenticate(code, requestUrl);
    }
}
