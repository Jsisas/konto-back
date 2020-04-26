package com.konto.konto.openBankingApi.providers.lhv.api.account.response;

import com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request.OpenBankingAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountBasicResponse {
    private List<OpenBankingAccount> accounts;
}
