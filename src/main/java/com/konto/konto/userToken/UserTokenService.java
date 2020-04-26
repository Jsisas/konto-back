package com.konto.konto.userToken;

import com.konto.konto.auth.AuthUtil;
import com.konto.konto.crypt.CryptService;
import com.konto.konto.openBankingApi.model.OpenBankingProviderName;
import com.konto.konto.user.User;
import com.konto.konto.user.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final UserTokenDao userTokenDao;
    private final CryptService cryptService;

    public void upsert(UserToken token){
        if(token.getId() != null){
            userTokenDao.update(cryptUserToken(token));
        }else{
            userTokenDao.insert(cryptUserToken(token));
        }

        User user = AuthUtil.getCurrentUser();
        user.getTokens().removeIf((userToken) -> userToken.getProvider().equals(OpenBankingProviderName.LHV));
        user.getTokens().add(token);
    }

    public List<UserToken> getByUserId(int userId){
        return userTokenDao.getByUserId(userId);
    }

    private UserToken cryptUserToken(UserToken token){
        token.setAccessToken(cryptService.encrypt(token.getAccessToken()));
        token.setRefreshToken(cryptService.decrypt(token.getRefreshToken()));
        return token;
    }
}
