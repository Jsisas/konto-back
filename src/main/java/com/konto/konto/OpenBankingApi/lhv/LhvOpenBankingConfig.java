package com.konto.konto.OpenBankingApi.lhv;

import com.konto.konto.keystore.KeyStoreUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

@Configuration
public class LhvOpenBankingConfig {

    private final String keyStoreLocation = "classpath:certs/lhv-combined.jks";
    private final String keyStorePassword = "asd123";
    public static String API_URL = "https://sandboxapi.lhv.eu/psd2";
    public static String CLIENT_ID = "PSDEE-LHVTEST-cfcdb0";

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