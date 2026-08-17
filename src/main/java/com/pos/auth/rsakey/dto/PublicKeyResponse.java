package com.pos.auth.rsakey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyResponse {
    private String keyId;
    private String publicKeyString;
}
