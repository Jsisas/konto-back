package com.konto.konto.userConsent;

import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConsentService {

    private final UserConsentDao userConsentDao;

    public UserConsent getUserConsentByUserIdAndProvider(int userId, OpenBankingProviderName provider){
        return userConsentDao.getByUserIdAndProvider(userId, provider);
    }

}
