package com.pos.auth.authloginflow.controller;

import com.pos.auth.authloginflow.dto.LoginRequest;
import com.pos.auth.authloginflow.dto.LoginResponse;
import com.pos.auth.authloginflow.service.AuthLoginFlowService;
import com.pos.auth.rsakey.dto.PublicKeyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController;
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor;
public class AuthController {

    private final AuthLoginFlowService authLoginFlowService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authLoginFlowService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public-key")
    public ResponseEntity<PublicKeyResponse> getPublicKey() {
        PublicKeyResponse response = authLoginFlowService.getPublicKey();
        return ResponseEntity.ok(response);
    }
}