package com.oopsmails.common.annotation.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class HmacConverter {

    private static final String ALGORITHM_HMAC_SHAR256 = "HmacSHA256";
    private static final Charset CHAR_SET = StandardCharsets.UTF_8;

    public String digest(String message, String key) {
        log.info("Passed in message: {}, key: {}", message, key);
        try {
            // 1. Get an algorithm instance.
            final Mac algorithm = Mac.getInstance(ALGORITHM_HMAC_SHAR256);

            // 2. Create secret key.
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHAR_SET), ALGORITHM_HMAC_SHAR256);

            // 3. Assign secret key algorithm.
            algorithm.init(secretKey);

            // 4. Generate Base64 encoded cipher string.
            String hash = new String(Hex.encodeHex(algorithm.doFinal(message.getBytes(CHAR_SET))));

            log.info("Using algorithm: {}, hash: {}", algorithm, hash);
            return hash;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }
}
