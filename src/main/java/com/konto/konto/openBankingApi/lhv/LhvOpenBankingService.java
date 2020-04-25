package com.konto.konto.openBankingApi.lhv;

import com.konto.konto.openBankingApi.OpenBankingAuth;
import com.konto.konto.openBankingApi.OpenBankingProvider;
import com.konto.konto.openBankingApi.lhv.api.auth.LhvApiAuthService;
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

    public ResponseEntity<OpenBankingAuth> authenticate(String code, String redirectUrl) {
        ResponseEntity<OpenBankingAuth> authResponse = lhvApiAuthService.authenticate(code, redirectUrl);

        if (authResponse.hasBody() && authResponse.getBody() != null) {
            UserToken userToken = new UserToken(authResponse.getBody(), OpenBankingProvider.LHV);
            userTokenService.upsert(userToken);
        }
        return authResponse;
    }
}
