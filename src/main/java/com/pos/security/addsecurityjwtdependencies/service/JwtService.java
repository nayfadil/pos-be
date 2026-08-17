package com.pos.security.addsecurityjwtdependencies.service;

import java.util.Map;

public interface JwtService {
    String generateToken(String username, Map<String, Object> extraClaims);
    String extractUsername(String token);
    boolean isTokenValid(String token, String username);
}
