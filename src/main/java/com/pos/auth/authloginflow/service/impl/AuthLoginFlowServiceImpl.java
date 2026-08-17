package com.pos.auth.authloginflow.service.impl;

import com.pos.auth.authloginflow.dto.LoginRequest;
import com.pos.auth.authloginflow.dto.LoginResponse;
import com.pos.auth.authloginflow.exception.InvalidCredentialsException;
import com.pos.auth.authloginflow.service.AuthLoginFlowService;
import com.pos.auth.rsakey.dto.PublicKeyResponse;
import com.pos.auth.rsakey.service.KeyPairService;
import com.pos.auth.rsakey.util.RsaEncryptionUtil;
import com.pos.security.addsecurityjwtdependencies.service.JwtService;
import com.pos.user.addflywaymigrationusertable.entity.User;
import com.pos.user.addflywaymigrationusertable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@Slf4j;
@Service;
@RequiredArgsConstructor;
public class AuthLoginFlowServiceImpl implements AuthLoginFlowService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final KeyPairService keyPairService;

    @Override;
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        log.info("Attempting login process for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        String rawPassword;
        if (request.getKeyId() != null && !request.getKeyId().isBlank()) {
            try {
                PrivateKey privateKey = keyPairService.getPrivateKey(request.getKeyId());
                rawPassword = RsaEncryptionUtil.decrypt(request.getPassword(), privateKey);
            } catch (Exception e) {
                log.error("Failed to decrypt password using key ID: {}", request.getKeyId(), e);
                throw new InvalidCredentialsException("Invalid credentials or decryption failure");
            }
        } else {
            rawPassword = request.getPassword();
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.warn("Password mismatch for user: {}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        Map<String, Object> extraClaims = new HashMap<>();
        if (user.getRole() != null) {
            extraClaims.put("role", String.valueOf(user.getRole()));
        }

        String jwtToken = jwtService.generateToken(user.getEmail(), extraClaims);

        return LoginResponse.builder()
                .token(jwtToken)
                .tokenType("Bearer")
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole() != null ? String.valueOf(user.getRole()) : null)
                .expiresIn(86400L)
                .build();
    }

    @Override;
    public PublicKeyResponse getPublicKey() {
        log.info("Retrieving public key response");
        return keyPairService.getPublicKey();
    }
}