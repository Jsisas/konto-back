package com.konto.konto.userConsent;

import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserConsent {

    private int id;
    private String consentId;
    private OpenBankingProviderName provider;

}
