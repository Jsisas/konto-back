package com.konto.konto.openBankingApi.providers.lhv.api.consent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konto.konto.auth.AuthUtil;
import com.konto.konto.crypt.CryptService;
import com.konto.konto.openBankingApi.OpenBankingUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.OpenBankingConsentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LhvApiConsentService {

    private final RestTemplate lhvRestTemplate;
    private final CryptService cryptService;
    private final ObjectMapper objectMapper;
    public ResponseEntity<String> getConsent(OpenBankingConsentRequest requestObj, String redirectUrl, String clientIp){
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

        ResponseEntity<String> exchange = lhvRestTemplate.exchange(
                "/v1/consents",
                HttpMethod.POST,
                request,
                String.class
        );
        return exchange;
    }

}
