package com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenBankingConsentAccount {
        private String iban;
}
