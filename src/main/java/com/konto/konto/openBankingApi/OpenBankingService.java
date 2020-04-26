package com.konto.konto.openBankingApi;

import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenBankingService {

    public final static String OPEN_BANKING_API_ENDPOINT = "/api/openbanking";

    public List<OpenBankingProvider> getOpenBankingProviderInfo() {
        return Arrays.stream(OpenBankingProviderName.values())
                .map(providerName -> new OpenBankingProvider(providerName, getBankRedirectUrl(providerName)))
                .collect(Collectors.toList());
    }

    private String getBankRedirectUrl(OpenBankingProviderName providerName){
        return OpenBankingService.OPEN_BANKING_API_ENDPOINT + "/" + providerName.name().toLowerCase() + "/redirect";
    }

}
