package com.konto.konto.openBankingApi;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.user.User;
import com.konto.konto.user.UserToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenBankingUtil {

    public UserToken getTokenByProvider(User user, OpenBankingProviderName providerName){
        return user.getTokens().stream().filter(token -> token.getProvider().equals(providerName)).findFirst().orElse(null);
    }

}
