package com.pos.auth.authloginflow.service.impl;

import com.pos.auth.authloginflow.dto.LoginRequest;
import com.pos.auth.authloginflow.dto.LoginResponse;
import com.pos.auth.authloginflow.exception.InvalidCredentialsException;
import com.pos.auth.authloginflow.service.AuthLoginFlowService;
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

    private final UserRepository userRepository;
    private final KeyPairService keyPairService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        PrivateKey privateKey = keyPairService.getPrivateKey();

        String decryptedPassword;
        try {
            decryptedPassword = RsaEncryptionUtil.decrypt(request.getEncryptedPassword(), privateKey);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Failed to decrypt password using RSA key");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(decryptedPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
