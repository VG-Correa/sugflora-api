package com.spec.api_sugflora.model.interfaces;


public interface DTOConvertable<T> {
    T toDTO();
    void InitByDTO(T dto);
}
