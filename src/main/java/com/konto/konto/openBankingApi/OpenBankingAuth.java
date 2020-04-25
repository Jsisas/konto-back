package com.konto.konto.openBankingApi;

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
