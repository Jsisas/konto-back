package com.konto.konto.openBankingApi.providers.lhv;

import com.konto.konto.openBankingApi.OpenBankingService;
import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.openBankingApi.providers.lhv.api.account.LhvApiAccountService;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.LhvApiConsentService;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request.OpenBankingConsentRequest;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.response.ConsentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/openbanking/lhv")
public class LhvOpenBankingController {

    @Value("${app.front.url}")
    private String frontUrl;
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
    @PostMapping("/consent")
    public void lhvConsent(HttpServletResponse res, @RequestBody OpenBankingConsentRequest requestObj) {
        ResponseEntity<ConsentResponse> consent = lhvApiConsentService.getConsent(requestObj, "http://localhost:3000", "127.0.0.1");
        if(consent != null && consent.getBody() != null){
            String consentRedirectLink = consent.getBody().get_links().getScaRedirect().getHref();
            res.setHeader("Location", consentRedirectLink);
            res.setStatus(302);
        }
    }

    @ResponseBody
    @GetMapping("/consent/{consentId}/status")
    public ResponseEntity<String> lhvConsent(@PathVariable String consentId) {
        return lhvApiConsentService.getConsentStatus(consentId);
    }

    @ResponseBody
    @GetMapping("/accounts-list")
    public ResponseEntity<String> accountsList() {
        return lhvApiAccountService.getBasicAccounts();
    }

}
