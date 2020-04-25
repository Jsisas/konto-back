package com.konto.konto.openBankingApi;

import com.konto.konto.user.UserToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenBankingAuth {
    String access_token;
    String token_type;
    String refresh_token;
    int expires_in;
    String scope;
}
