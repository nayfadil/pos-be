package com.pos.auth.rsakey.service;

import com.pos.auth.rsakey.dto.PublicKeyResponse;
import java.security.PrivateKey;

public interface KeyPairService {
    PublicKeyResponse generateKeyPair();
    PrivateKey getPrivateKey(String keyId);
    String decryptPassword(String keyId, String encryptedPassword);
}
