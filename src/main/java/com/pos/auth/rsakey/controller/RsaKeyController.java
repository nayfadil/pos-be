package com.pos.auth.rsakey.controller;

import com.pos.auth.rsakey.dto.PublicKeyResponse;
import com.pos.auth.rsakey.service.KeyPairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/rsa")
@Tag(name = "RSA Key Management", description = "Endpoints for dynamic RSA key pair generation and public key retrieval")
public class RsaKeyController {

    private final KeyPairService keyPairService;

    public RsaKeyController(KeyPairService keyPairService) {
        this.keyPairService = keyPairService;
    }

    @GetMapping("/public-key")
    @Operation(summary = "Generate and retrieve a dynamic RSA Public Key with UUID KeyID")
    public ResponseEntity<PublicKeyResponse> getPublicKey() {
        PublicKeyResponse response = keyPairService.generateKeyPair();
        return ResponseEntity.ok(response);
    }
}
