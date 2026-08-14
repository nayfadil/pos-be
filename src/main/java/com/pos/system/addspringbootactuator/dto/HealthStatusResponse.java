package com.pos.system.addspringbootactuator.dto;

import java.time.LocalDateTime;

public record HealthStatusResponse(
    String status,
    String message,
    LocalDateTime timestamp
) {}