package com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenBankingAccount {
    private String name;
    private String iban;
    private String currency;
}
