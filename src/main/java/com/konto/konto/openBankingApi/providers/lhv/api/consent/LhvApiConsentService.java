package com.konto.konto.openBankingApi.providers.lhv.api.consent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.auth.AuthUtil;
import com.konto.konto.crypt.CryptService;
import com.konto.konto.openBankingApi.OpenBankingUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request.OpenBankingConsentRequest;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.response.ConsentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LhvApiConsentService {

    private final RestTemplate lhvRestTemplate;
    private final CryptService cryptService;
    private final ObjectMapper objectMapper;

    public ResponseEntity<ConsentResponse> getConsent(OpenBankingConsentRequest requestObj, String redirectUrl, String clientIp){
        String cryptedAccessToken = OpenBankingUtil.getTokenByProvider(AuthUtil.getCurrentUser(), OpenBankingProviderName.LHV).getAccessToken();
        String accessToken = cryptService.decrypt(cryptedAccessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.add("X-Request-ID", UUID.randomUUID().toString());
        headers.add("PSU-IP-Address", clientIp);
        headers.add("TPP-Redirect-URI", redirectUrl);

        String requestJson = null;
        try {
            requestJson = objectMapper.writeValueAsString(requestObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        return lhvRestTemplate.postForEntity("/v1/consents", request, ConsentResponse.class);
    }

    public ResponseEntity<String> getConsentStatus(String consentId){
        String cryptedAccessToken = OpenBankingUtil.getTokenByProvider(AuthUtil.getCurrentUser(), OpenBankingProviderName.LHV).getAccessToken();
        String accessToken = cryptService.decrypt(cryptedAccessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.add("X-Request-ID", UUID.randomUUID().toString());

        HttpEntity<String> request = new HttpEntity<>(headers);

        return lhvRestTemplate.exchange(
                "/v1/consents/" + consentId + "/status",
                HttpMethod.GET,
                request,
                String.class
        );

    }

}
