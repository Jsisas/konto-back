package com.konto.konto.userToken;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.crypt.CryptService;
import com.konto.konto.openBankingApi.OpenBankingUtil;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.user.User;
import com.konto.konto.user.UserService;
import com.konto.konto.user.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final UserTokenDao userTokenDao;
    private final CryptService cryptService;

    public void upsert(UserToken token) {
        User user = AuthUtil.getCurrentUser();
        UserToken storedToken = OpenBankingUtil.getTokenByProvider(user, token.getProvider());
        if (storedToken != null && storedToken.getId() != null) {
            userTokenDao.update(cryptUserToken(token));
        } else {
            userTokenDao.insert(cryptUserToken(token));
        }

        if (user.getTokens().contains(token)) {
            user.getTokens().removeIf((userToken) -> userToken.getProvider().equals(OpenBankingProviderName.LHV));
        }
    }

    public List<UserToken> getByUserId(int userId) {
        return userTokenDao.getByUserId(userId);
    }

    private UserToken cryptUserToken(UserToken token) {
        token.setAccessToken(cryptService.encrypt(token.getAccessToken()));
        token.setRefreshToken(cryptService.encrypt(token.getRefreshToken()));
        return token;
    }
}
