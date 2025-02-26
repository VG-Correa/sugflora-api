package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;

import com.spec.api_sugflora.model.IntDomain;

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

    public void copyDomainModel(IntDomain model) {
        this.id = model.getId();
        this.createdAt = model.getCreatedAt();
        this.createdById = model.getCreatedById();
        this.deletedById = model.getDeletedById();
        this.deletedAt = model.getDeletedAt();
        this.updatedAt = model.getUpdatedAt();
        this.updatedById = model.getUpdatedById();
    }
}
