package com.konto.konto.openBankingApi.providers.lhv;

import com.konto.konto.openBankingApi.model.OpenBankingAuthResponse;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.openBankingApi.providers.lhv.api.auth.LhvApiAuthService;
import com.konto.konto.user.UserToken;
import com.konto.konto.userToken.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LhvOpenBankingService {

    private final UserTokenService userTokenService;
    private final LhvApiAuthService lhvApiAuthService;

    public ResponseEntity<OpenBankingAuthResponse> authenticate(String code, String redirectUrl) {
        ResponseEntity<OpenBankingAuthResponse> authResponse = lhvApiAuthService.authenticate(code, redirectUrl);

        if (authResponse.hasBody() && authResponse.getBody() != null) {
            UserToken userToken = new UserToken(authResponse.getBody(), OpenBankingProviderName.LHV);
            userTokenService.upsert(userToken);
        }
        return authResponse;
    }
}
