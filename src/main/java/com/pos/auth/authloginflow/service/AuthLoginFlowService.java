package com.pos.auth.authloginflow.service;

import com.pos.auth.authloginflow.dto.LoginRequest;
import com.pos.auth.authloginflow.dto.LoginResponse;
import com.pos.auth.rsakey.dto.PublicKeyResponse;

public interface AuthLoginFlowService {
    PublicKeyResponse getPublicKey();
    LoginResponse login(LoginRequest request);
}
