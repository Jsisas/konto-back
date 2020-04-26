package com.konto.konto.openBankingApi.providers.lhv;

import com.konto.konto.openBankingApi.OpenBankingService;
import com.konto.konto.openBankingApi.model.OpenBankingAuthResponse;
import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.openBankingApi.providers.lhv.api.account.LhvApiAccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    @Value("${app.front.url}")
    private String frontUrl;
    private final OpenBankingService openBankingService;
    private final LhvOpenBankingService lhvOpenBankingService;
    private final LhvApiAccountService lhvApiAccountService;

    @ResponseBody
    @GetMapping("/redirect")
    public void lhvRedirect(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "code", required = false) String code) {
        if(code != null && !code.isEmpty()){
            String requestUrl = request.getRequestURL().toString();
            lhvOpenBankingService.authenticate(code, requestUrl);
            if (openBankingService.currentUserTokenExists(new OpenBankingProvider(OpenBankingProviderName.LHV, openBankingService.getBankRedirectUrl(OpenBankingProviderName.LHV)))) {
                response.setHeader("Location", frontUrl + "?auth=success");
                response.setStatus(302);
            } else {
                response.setHeader("Location", frontUrl + "?auth=failed");
                response.setStatus(302);
            }
        }
    }

    @ResponseBody
    @GetMapping("/accounts-list")
    public ResponseEntity<String> accountsList() {
        return lhvApiAccountService.getBasicAccounts();
    }

}
