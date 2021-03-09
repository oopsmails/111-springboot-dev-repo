package com.oopsmails.springboot.jwt;

import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtTest extends SpringBootJavaGenericTestBase {

    @Test
    public void testStringEncodeDecode() throws Exception {
        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        assertEquals(originalInput, decodedString, "Decoded String should be same.");
    }

    @Test
    public void testUrlEncodeDecode() throws Exception {
        String originalUrl = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFV&gws_rd=ssl#q=java";
        String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());

        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl);
        String decodedUrl = new String(decodedBytes);

        assertEquals(originalUrl, decodedUrl, "Decoded Url should be same.");
    }

}
