package com.pos.monitoring.addspringbootactuator.dto;

public record HealthStatusResponse(
        String status,
        String message,
        long timestamp
) {}