package com.konto.konto.user;

import com.konto.konto.userToken.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserDao userDao;
    private final UserTokenService userTokenService;

    public User upsert(User user){
        if(user.getId() != null){
            userDao.update(user);
        }else{
            userDao.insert(user);
        }

        if(user.getTokens() != null && !user.getTokens().isEmpty()){
            user.getTokens().forEach(userTokenService::upsert);
        }

        return getUserByEmail(user.getEmail());
    }

    public boolean userExists(int userId){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(user == null){
            user = userDao.getUserById(userId);
        }
        
        return (user != null && user.getId() != null);
    }

    public User getUserByEmail(String email){
        User user = userDao.getUserByEmail(email);
        user.setTokens(userTokenService.getByUserId(user.getId()));
        return user;
    }

    public User getUserById(int userId){
        User user = userDao.getUserById(userId);
        user.setTokens(userTokenService.getByUserId(user.getId()));
        return user;
    }
    
}
