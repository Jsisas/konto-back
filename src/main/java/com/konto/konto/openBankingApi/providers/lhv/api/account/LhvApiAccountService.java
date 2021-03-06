package com.konto.konto.openBankingApi.providers.lhv.api.account;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.crypt.CryptService;
import com.konto.konto.openBankingApi.OpenBankingUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.openBankingApi.providers.lhv.api.account.response.AccountBasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LhvApiAccountService {

    private final RestTemplate lhvRestTemplate;
    private final CryptService cryptService;

    public ResponseEntity<AccountBasicResponse> getBasicAccounts(){
        String cryptedAccessToken = OpenBankingUtil.getTokenByProvider(AuthUtil.getCurrentUser(), OpenBankingProviderName.LHV).getAccessToken();
        String accessToken = cryptService.decrypt(cryptedAccessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.add("X-Request-ID", UUID.randomUUID().toString());

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);
        return lhvRestTemplate.exchange(
                "/v1/accounts-list",
                HttpMethod.GET,
                request,
                AccountBasicResponse.class
                );
    }

}
