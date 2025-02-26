package com.spec.api_sugflora.model.interfaces;

public interface DTOConvertable {
    DTO toDTO();
    boolean InitByDTO(DTO dto);
}
