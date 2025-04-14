package com.spec.api_sugflora.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spec.api_sugflora.dto.IntDomainDTO;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true, unique = false)
    private UUID createdById;

    @Column(nullable = false, unique = false)
    private boolean deleted;

    @Column(nullable = true, unique = false)
    private UUID deletedById;

    @Column(nullable = true, unique = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deletedAt;

    @Column(nullable = true, unique = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = true, unique = false)
    private UUID updatedById;

    public void copyDomainDTO(IntDomainDTO dto) {
        this.id = dto.getId();
        this.createdAt = dto.getCreatedAt();
        this.createdById = dto.getCreatedById();
        this.deletedById = dto.getDeletedById();
        this.deletedAt = dto.getDeletedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.updatedById = dto.getUpdatedById();
    }

    public void updateDateNow() {
        setUpdatedAt(LocalDateTime.now());

    }
}
