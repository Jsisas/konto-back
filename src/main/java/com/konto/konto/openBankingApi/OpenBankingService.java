package com.konto.konto.openBankingApi;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProvider;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenBankingService {

    public final static String OPEN_BANKING_API_ENDPOINT = "/api/openbanking";

    public List<OpenBankingProvider> getOpenBankingProviderInfo() {
        return Arrays.stream(OpenBankingProviderName.values())
                .map(providerName -> new OpenBankingProvider(providerName, getBankRedirectUrl(providerName)))
                .collect(Collectors.toList());
    }

    public boolean currentUserTokenExists(OpenBankingProvider provider){
        return OpenBankingUtil.getTokenByProvider(AuthUtil.getCurrentUser(), provider.getName()).getId() != null;
    }

    public String getBankRedirectUrl(OpenBankingProviderName providerName){
        return OpenBankingService.OPEN_BANKING_API_ENDPOINT + "/" + providerName.name().toLowerCase() + "/redirect";
    }

}
