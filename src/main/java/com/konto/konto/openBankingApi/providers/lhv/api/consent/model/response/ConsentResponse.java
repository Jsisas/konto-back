package com.konto.konto.openBankingApi.providers.lhv.api.consent.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsentResponse {
    private String consentStatus;
    private String consentId;
    private ConsentResponseLinks _links;
}
