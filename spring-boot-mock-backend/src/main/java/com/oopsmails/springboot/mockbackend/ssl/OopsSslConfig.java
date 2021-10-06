package com.oopsmails.springboot.mockbackend.ssl;

import com.oopsmails.springboot.mockbackend.exception.OopsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

@Configuration
@Slf4j
@ConditionalOnProperty("oopsmails.ssl.enabled")
public class OopsSslConfig {
    @Value(value = "${oopsmails.ssl.enabled:false}")
    private boolean sslEnabled;

    @Value(value = "${server.ssl.key-alias:''}")
    private String alias;

    @PostConstruct
    public void init() {
        //        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        //        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
        //        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        //        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    }

    @Bean
    public SSLConnectionSocketFactory oopsSslConnectionSocketFactory() {
        try {
            String identityKeyStorePath = "";
            String identityKeyStorePass = "";
            String identityKeyPass = "";
            KeyStore identityKeyStore = KeyStore.getInstance("jks");
            File identityKeyStoreFile = new File(StringUtils.removeStartIgnoreCase(identityKeyStorePath, "file:"));
            FileInputStream keyStoreFile = new FileInputStream(identityKeyStoreFile);
            identityKeyStore.load(keyStoreFile, identityKeyStorePass.toCharArray());

            String trustStorePath = "";
            String trustStorePass = "";
            KeyStore trustStore = KeyStore.getInstance("jks");
            FileInputStream trustStoreFile = new FileInputStream(new File(StringUtils.removeStartIgnoreCase(trustStorePath, "file:")));
            trustStore.load(keyStoreFile, trustStorePass.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .setKeyStoreType("JKS")
                    .loadKeyMaterial(identityKeyStoreFile, identityKeyStorePass.toCharArray(), identityKeyPass.toCharArray(), new PrivateKeyStrategy() {
                        @Override
                        public String chooseAlias(Map<String, PrivateKeyDetails> aliases, Socket socket) {
                            return alias;
                        }
                    })
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            return true;
                        }
                    })
                    .build();

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TlSv1.2", "TLSv1.1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            return sslConnectionSocketFactory;
        } catch (Exception e) {
            log.error("Error initializing SSLContext: " + e.getMessage(), e);
            throw new OopsException(e.getMessage(), e);
        }
    }
}
