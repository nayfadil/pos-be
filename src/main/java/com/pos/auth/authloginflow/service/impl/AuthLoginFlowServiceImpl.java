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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
@RequiredArgsConstructor
public class AuthLoginFlowServiceImpl implements AuthLoginFlowService {

    private final KeyPairService keyPairService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public PublicKeyResponse getPublicKey() {
        return keyPairService.generateKeyPair();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        PrivateKey privateKey = keyPairService.getPrivateKey(request.getKeyId());
        if (privateKey == null) {
            throw new InvalidCredentialsException("Invalid or expired RSA key ID");
        }

        String decryptedPassword;
        try {
            decryptedPassword = RsaEncryptionUtil.decrypt(request.getEncryptedPassword(), privateKey);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Failed to decrypt password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(decryptedPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        long expiresAt = jwtService.getExpirationTime();

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .expiresAt(expiresAt)
                .build();
    }
}
