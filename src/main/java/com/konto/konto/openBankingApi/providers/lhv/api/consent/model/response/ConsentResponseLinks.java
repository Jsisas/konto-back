package com.konto.konto.openBankingApi.providers.lhv.api.consent.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsentResponseLinks {
    private ConsentResponseLink status;
    private ConsentResponseLink scaRedirect;

}
