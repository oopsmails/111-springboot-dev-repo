package com.oopsmails.springboot.mockbackend.ssl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.text.MessageFormat;

/**
 * Ref:
 * https://stackoverflow.com/questions/30770280/spring-boot-ssl-client
 */

@Configuration
@Slf4j
@ConditionalOnProperty("oopsmails.ssl.enabled")
public class OopsRestTemplate extends RestTemplate {
    /**
     * The key store password.
     */
    private String keyStorePassword;

    /**
     * The key store location.
     */
    private String keyStoreLocation;

    /**
     * The rest template.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * The http components client http request factory.
     */
    private HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory;

    /**
     * Instantiates a new custom rest template.
     */
    public OopsRestTemplate() {
        super();
    }

    public OopsRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = getRestTemplate();
    }

    /**
     * Rest template.
     *
     * @return the rest template
     */
    public RestTemplate getRestTemplate() {
        if (null == httpComponentsClientHttpRequestFactory) {
            httpComponentsClientHttpRequestFactory = loadCert();
            restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory);
        }
        return restTemplate;
    }

    /**
     * Sets the rest template.
     *
     * @param restTemplate the new rest template
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Load cert.
     *
     * @return the http components client http request factory
     */
    private HttpComponentsClientHttpRequestFactory loadCert() {
        try {
            char[] keypass = keyStorePassword.toCharArray();
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadKeyMaterial(getkeyStore(keyStoreLocation, keypass), keypass)
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
            httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
            httpComponentsClientHttpRequestFactory.setConnectTimeout(5000);
            httpComponentsClientHttpRequestFactory.setReadTimeout(30000);
        } catch (Exception ex) {
            log.error(MessageFormat.format("Some Error", ex.getMessage()), ex);

        }
        return httpComponentsClientHttpRequestFactory;
    }

    /**
     * Key store.
     *
     * @param storePath the store path
     * @param password  the password
     * @return the key store
     */
    private KeyStore getkeyStore(String storePath, char[] password) {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            File key = ResourceUtils.getFile(storePath);
            try (InputStream in = new FileInputStream(key)) {
                keyStore.load(in, password);
            }
        } catch (Exception ex) {
            log.error(MessageFormat.format("Some Error", ex.getMessage()), ex);

        }
        return keyStore;
    }

    /**
     * Sets the key store password.
     *
     * @param keyStorePassword the new key store password
     */
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * Sets the key store location.
     *
     * @param keyStoreLocation the new key store location
     */
    public void setKeyStoreLocation(String keyStoreLocation) {
        this.keyStoreLocation = keyStoreLocation;
    }
}
