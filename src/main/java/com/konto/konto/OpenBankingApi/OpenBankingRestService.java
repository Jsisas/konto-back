package com.konto.konto.OpenBankingApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class OpenBankingRestService {

    private final String keyStoreLocation = "classpath:certs/lhv-combined.jks";
    private final String keyStorePassword = "asd123";
    public static String API_URL = "https://sandboxapi.lhv.eu/psd2";
    public static String CLIENT_ID = "PSDEE-LHVTEST-cfcdb0";

    @Bean
    public RestTemplate lhvRestTemplate() throws Exception {
        final KeyStore keyStore = loadKeyStore(keyStoreLocation, keyStorePassword);
        List<String> keyAliases = Collections.list(keyStore.aliases());
        log.info("LHV Connect keystore contains following aliases: " + StringUtils.join(keyAliases, ','));
        SSLContext sslContext = new SSLContextBuilder()
                .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setSSLContext(sslContext)
                .build();

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(API_URL));
        return restTemplate;
    }

    private KeyStore loadKeyStore(String location, String password) throws Exception {
        try (
                InputStream is = new FileInputStream(ResourceUtils.getFile(location));
        ) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, password.toCharArray());
            return keyStore;
        }
    }

}
