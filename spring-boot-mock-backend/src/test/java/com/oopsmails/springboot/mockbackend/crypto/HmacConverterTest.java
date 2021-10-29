package com.oopsmails.springboot.mockbackend.crypto;

import com.oopsmails.common.annotation.crypto.HmacConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class HmacConverterTest {

    @InjectMocks
    private HmacConverter hmacConverter;

    @Test
    public void testDigest() throws Exception {
        String message = "crypto-payload";
        String key = "12345678";

        String result = hmacConverter.digest(message, key);
        System.out.printf("result: [{}]\n", result);

        assertNotNull(result, "result should not be null.");
    }
}
