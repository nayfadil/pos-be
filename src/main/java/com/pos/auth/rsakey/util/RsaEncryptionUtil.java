package com.pos.auth.rsakey.util;

import com.pos.auth.rsakey.exception.InvalidRsaKeyException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Base64;

@Component
public class RsaEncryptionUtil {

    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    public String decrypt(String encryptedTextBase64, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = Base64.getDecoder().decode(encryptedTextBase64);
            byte[] decryptedBytes = cipher.doFinal(bytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new InvalidRsaKeyException("Failed to decrypt data with provided RSA key", e);
        }
    }
}
