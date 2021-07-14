package com.oopsmails.springboot.mockbackend.main;

import com.oopsmails.springboot.mockbackend.utils.SslUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Slf4j
public class MainSslUtils {
    public static void main(String[] args) {
        boolean isInKeyStore = false;
        try {
            isInKeyStore = SslUtils.isSslCertIsInKeystore("abc", "/path/to/cert.crt");
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        log.info("isInKeyStore = {}", isInKeyStore);
    }
}
