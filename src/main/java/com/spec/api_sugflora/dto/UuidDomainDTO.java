package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public abstract class UuidDomainDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private Integer createdById;
    private Integer deletedById;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
    private Integer updatedById;
}
