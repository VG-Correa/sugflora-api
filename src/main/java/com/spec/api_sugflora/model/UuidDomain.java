package com.spec.api_sugflora.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public abstract class UuidDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = false)
    private LocalDateTime createdAt;

    @Column(nullable = true, unique = false)
    private Integer createdById;

    @Column(nullable = true, unique = false)
    private Integer deletedById;

    @Column(nullable = true, unique = false)
    private LocalDateTime deletedAt;

    @Column(nullable = true, unique = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true, unique = false)
    private Integer updatedById;
}
