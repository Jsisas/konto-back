package com.konto.konto.openBankingApi.providers.lhv.api;

import com.konto.konto.util.KeyStoreUtil;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

@Configuration
public class LhvApiConfig {

    @Value("${openbanking.lhv.key-store}")
    private String keyStoreLocation;
    @Value("${openbanking.lhv.key-store-password}")
    private String keyStorePassword;
    @Value("${openbanking.lhv.api-url}")
    public String API_URL;
    @Value("${openbanking.lhv.client-id}")
    public static String CLIENT_ID;

    @Bean
    public RestTemplate lhvRestTemplate() throws Exception {
        final KeyStore keyStore = KeyStoreUtil.loadKeyStore(keyStoreLocation, keyStorePassword);

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


}
