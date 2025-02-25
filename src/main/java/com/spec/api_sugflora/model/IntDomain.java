package com.spec.api_sugflora.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class IntDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
