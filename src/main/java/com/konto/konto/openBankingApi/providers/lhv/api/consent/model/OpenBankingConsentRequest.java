package com.konto.konto.openBankingApi.providers.lhv.api.consent.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OpenBankingConsentRequest {
    private OpenBankingConsentAccess access;
    private boolean combinedServiceIndicator = false;
    private int frequencyPerDay = 4;
    private boolean recurringIndicator = true;
    private LocalDate validUntil;
}
