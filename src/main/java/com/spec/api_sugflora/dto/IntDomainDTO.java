package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class IntDomainDTO {
    private Integer id;
    private LocalDateTime createdAt;
    private Integer createdById;
    private Integer deletedById;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
    private Integer updatedById;
}
