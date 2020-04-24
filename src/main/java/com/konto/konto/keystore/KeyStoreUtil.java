package com.konto.konto.keystore;

import lombok.RequiredArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@RequiredArgsConstructor
public class KeyStoreUtil {

    public static KeyStore loadKeyStore(String location, String password) throws Exception {
        try (
                InputStream is = new FileInputStream(ResourceUtils.getFile(location));
        ) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, password.toCharArray());
            return keyStore;
        }
    }

}
