package com.konto.konto.openBankingApi.providers.lhv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.openBankingApi.OpenBankingService;
import com.konto.konto.openBankingApi.providers.lhv.api.account.LhvApiAccountService;
import com.konto.konto.openBankingApi.providers.lhv.api.account.response.AccountBasicResponse;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.LhvApiConsentService;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request.OpenBankingAccount;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request.OpenBankingConsentRequest;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.response.ConsentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    @Value("${app.front.url}")
    private String frontUrl;
    private final ObjectMapper objectMapper;
    private final OpenBankingService openBankingService;
    private final LhvOpenBankingService lhvOpenBankingService;
    private final LhvApiConsentService lhvApiConsentService;
    private final LhvApiAccountService lhvApiAccountService;

    @ResponseBody
    @GetMapping("/auth-redirect")
    public void lhvRedirect(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "code", required = false) String code) {
        if (code != null && !code.isEmpty()) {
            String requestUrl = request.getRequestURL().toString();
            lhvOpenBankingService.authenticate(code, requestUrl);

            ResponseEntity<ConsentResponse> consent = lhvApiConsentService.getConsent(
                    new OpenBankingConsentRequest(),
                    frontUrl + "?consent=success",
                    request.getRemoteAddr()
            );
            if (consent != null && consent.getBody() != null) {
                String consentRedirectLink = consent.getBody().get_links().getScaRedirect().getHref();
                response.setHeader("Location", consentRedirectLink);
                response.setStatus(302);
            }
        }
    }

    @ResponseBody
    @GetMapping("/consent/{consentId}/status")
    public ResponseEntity<String> lhvConsent(@PathVariable String consentId) {
        return lhvApiConsentService.getConsentStatus(consentId);
    }
}
