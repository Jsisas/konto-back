package com.konto.konto.user;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserDao userDao;
    
    public boolean userExists(int userId){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(user == null){
            user = userDao.getUserById(userId);
        }
        
        return (user != null && user.getId() != null);
    }
    
}
