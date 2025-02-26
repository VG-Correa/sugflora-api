package com.spec.api_sugflora.model.interfaces;

public interface DTO {
    DTOConvertable toModel();
    boolean initByModel(DTOConvertable model);
}
