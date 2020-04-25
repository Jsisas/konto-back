package com.konto.konto.crypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CryptService {

    @Value("${app.crypt-secret}")
    private String cryptSecret;
    @Value("${app.crypt-salt}")
    private String cryptSalt;

    public String encrypt(String textToEncrypt){
        return CryptUtil.encrypt(textToEncrypt, cryptSecret, cryptSalt);
    }

    public String decrypt(String textToEncrypt){
        return CryptUtil.decrypt(textToEncrypt, cryptSecret, cryptSalt);
    }
}
