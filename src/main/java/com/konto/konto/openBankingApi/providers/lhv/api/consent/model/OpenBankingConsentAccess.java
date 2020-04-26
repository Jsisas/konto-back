package com.konto.konto.openBankingApi.providers.lhv.api.consent.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenBankingConsentAccess {
    private String availableAccounts = "allAccountsWithBalances";
    private List<OpenBankingAccount> balances;
    private List<OpenBankingAccount> transactions;
}
