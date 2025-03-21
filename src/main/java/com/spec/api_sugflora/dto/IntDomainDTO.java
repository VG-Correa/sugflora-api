package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spec.api_sugflora.model.IntDomain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public abstract class IntDomainDTO {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;
    private Integer createdById;
    private Integer deletedById;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deletedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
