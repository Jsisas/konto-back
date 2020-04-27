package com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenBankingConsentAccess {
    private String availableAccounts = "allAccountsWithBalances";
    private List<OpenBankingConsentAccount> balances;
    private List<OpenBankingConsentAccount> transactions;
}
