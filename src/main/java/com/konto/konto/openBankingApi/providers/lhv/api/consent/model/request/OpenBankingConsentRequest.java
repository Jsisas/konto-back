package com.konto.konto.openBankingApi.providers.lhv.api.consent.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OpenBankingConsentRequest {
    private OpenBankingConsentAccess access = new OpenBankingConsentAccess();
    private boolean combinedServiceIndicator = false;
    private int frequencyPerDay = 4;
    private boolean recurringIndicator = true;
    private LocalDate validUntil = LocalDate.now().plusYears(1);
}
