package com.konto.konto.user;

import com.konto.konto.openBankingApi.model.OpenBankingAuthResponse;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    
    private Integer id;
    private String accessToken;
    private LocalDateTime accessTokenExpiration;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiration;
    private String scope;
    private OpenBankingProviderName provider;

    public UserToken(OpenBankingAuthResponse oauthToken, OpenBankingProviderName provider){
        this.accessToken = oauthToken.getAccess_token();
        this.accessTokenExpiration = LocalDateTime.now().plusSeconds(oauthToken.getExpires_in());
        this.refreshToken = oauthToken.getRefresh_token();
        this.refreshTokenExpiration = LocalDateTime.now().plusDays(90);
        this.scope = oauthToken.getScope();
        this.provider = provider;
    }
}
