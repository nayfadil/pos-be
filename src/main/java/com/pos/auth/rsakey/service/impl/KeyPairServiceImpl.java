package com.pos.auth.rsakey.service.impl;

import com.pos.auth.rsakey.dto.PublicKeyResponse;
import com.pos.auth.rsakey.exception.InvalidRsaKeyException;
import com.pos.auth.rsakey.service.KeyPairService;
import com.pos.auth.rsakey.util.RsaEncryptionUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KeyPairServiceImpl implements KeyPairService {

    private static final long KEY_EXPIRATION_MS = 15 * 60 * 1000;
    private final Map<String, CacheEntry> keyCache = new ConcurrentHashMap<>();
    private final RsaEncryptionUtil rsaEncryptionUtil;

    public KeyPairServiceImpl(RsaEncryptionUtil rsaEncryptionUtil) {
        this.rsaEncryptionUtil = rsaEncryptionUtil;
    }

    private static class CacheEntry {
        private final PrivateKey privateKey;
        private final Instant expirationTime;

        public CacheEntry(PrivateKey privateKey, Instant expirationTime) {
            this.privateKey = privateKey;
            this.expirationTime = expirationTime;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public boolean isExpired() {
            return Instant.now().isAfter(expirationTime);
        }
    }

    @Override
    public PublicKeyResponse generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            String keyId = UUID.randomUUID().toString();
            Instant expirationTime = Instant.now().plusMillis(KEY_EXPIRATION_MS);

            keyCache.put(keyId, new CacheEntry(keyPair.getPrivate(), expirationTime));

            String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            return PublicKeyResponse.builder()
                    .keyId(keyId)
                    .publicKeyString(publicKeyBase64)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating RSA key pair", e);
        }
    }

    @Override
    public PrivateKey getPrivateKey(String keyId) {
        CacheEntry entry = keyCache.get(keyId);
        if (entry == null || entry.isExpired()) {
            if (entry != null) {
                keyCache.remove(keyId);
            }
            throw new InvalidRsaKeyException("RSA key not found or has expired for keyId: " + keyId);
        }
        return entry.getPrivateKey();
    }

    @Override
    public String decryptPassword(String keyId, String encryptedPassword) {
        PrivateKey privateKey = getPrivateKey(keyId);
        return rsaEncryptionUtil.decrypt(encryptedPassword, privateKey);
    }

    @Scheduled(fixedRate = 300000)
    public void cleanupExpiredKeys() {
        keyCache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
