package com.konto.konto.openBankingApi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OpenBankingProvider {

    private OpenBankingProviderName name;
    private String redirectUrl;

}
