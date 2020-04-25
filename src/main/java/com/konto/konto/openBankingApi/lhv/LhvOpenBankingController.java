package com.konto.konto.openBankingApi.lhv;

import com.konto.konto.openBankingApi.OpenBankingAuth;
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
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    private final LhvOpenBankingService lhvOpenBankingService;

    @ResponseBody
    @GetMapping("/redirect")
    public ResponseEntity<OpenBankingAuth> lhvRedirect(HttpServletRequest request, @RequestParam(value = "code") String code) {
        String requestUrl = request.getRequestURL().toString();
        return lhvOpenBankingService.authenticate(code, requestUrl);
    }

}
