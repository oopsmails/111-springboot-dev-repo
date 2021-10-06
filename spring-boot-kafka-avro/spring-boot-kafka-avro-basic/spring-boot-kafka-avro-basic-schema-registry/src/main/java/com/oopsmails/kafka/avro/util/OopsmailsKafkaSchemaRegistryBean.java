package com.oopsmails.kafka.avro.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

@Component
@Slf4j
public class OopsmailsKafkaSchemaRegistryBean {
    @Autowired
    private Properties oopsmailsKafkaConsumerFactoryProps;

    @Value(value = "${oopsmails.kafka.bootstrap-servers.secure:true}")
    private boolean bootstrapSecure;

    @Value(value = "${oopsmails.kafka.ignore.schema.registry.url.check:false}")
    private boolean ignoreSchemaRegistryUrlCheck;

    @Value(value = "${server.ssl.key-alias:''}")
    private String alias;

    @Value(value = "${oopsmails.kafka.schema.registry.url:''}")
    private String schemaRegistryUrl;

    private SSLConnectionSocketFactory sslConnectionSocketFactory = null;

    @PostConstruct
    private void configureSsl() throws KeyManagementException, java.io.IOException, java.security.cert.CertificateException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        if (bootstrapSecure) {
            String identityKeyStorePath = oopsmailsKafkaConsumerFactoryProps.getProperty("oopsmails.kafka.keystore.path");
            String identityKeyStorePass = oopsmailsKafkaConsumerFactoryProps.getProperty("oopsmails.kafka.keystore.password");
            KeyStore identityKeyStore = KeyStore.getInstance("jks");
            FileInputStream keyStoreFile = new FileInputStream(new File(StringUtils.removeStartIgnoreCase(identityKeyStorePath, "file:")));
            identityKeyStore.load(keyStoreFile, identityKeyStorePass.toCharArray());

            String trustStorePath = oopsmailsKafkaConsumerFactoryProps.getProperty("oopsmails.kafka.truststore.path");
            String trustStorePass = oopsmailsKafkaConsumerFactoryProps.getProperty("oopsmails.kafka.truststore.password");
            KeyStore trustStore = KeyStore.getInstance("jks");
            FileInputStream trustStoreFile = new FileInputStream(new File(StringUtils.removeStartIgnoreCase(trustStorePath, "file:")));
            trustStore.load(keyStoreFile, trustStorePass.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(identityKeyStore, identityKeyStorePass.toCharArray(), new PrivateKeyStrategy() {
                            @Override
                            public String chooseAlias(Map<String, PrivateKeyDetails> map, Socket socket) {
                                return alias;
                            }
                        })
                    .loadTrustMaterial(trustStore, null).build();

            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[]{"TlSv1.2", "TLSv1.1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        }
    }

    public void runOopsmailsKafkaSchemaRegistryHealthyCheck(Runnable onHealthy, Consumer<Throwable> onNotHealthy) {
        if (ignoreSchemaRegistryUrlCheck) {
            onHealthy.run();
            return;
        }

        CloseableHttpClient httpClient = null;
        try {
            httpClient = getCloseableHttpClient();
            HttpUriRequest httpUriRequest = new HttpGet(schemaRegistryUrl);
            CloseableHttpResponse resp = httpClient.execute(httpUriRequest);

            if (resp.getStatusLine().getStatusCode() >= 200 && resp.getStatusLine().getStatusCode() < 300) {
                onHealthy.run();
            } else {
                if (onNotHealthy != null) {
                    onNotHealthy.accept(null);
                }
            }
        } catch (Throwable throwable) {
            log.warn(throwable.getMessage(), throwable);
            if (onNotHealthy != null) {
                onNotHealthy.accept(throwable);
            }
        } finally {
            close(httpClient);
        }
    }

    private void close(CloseableHttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Throwable throwable) {
                log.warn(throwable.getMessage(), throwable);
            }
        }
    }

    private CloseableHttpClient getCloseableHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (bootstrapSecure) {
            httpClientBuilder = httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
        }
        return httpClientBuilder.build();
    }
}
