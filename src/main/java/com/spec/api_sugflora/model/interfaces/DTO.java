package com.spec.api_sugflora.model.interfaces;

import java.lang.reflect.Field;

public interface DTO<T> {
    T toModel();
    void initByModel(T model);

    
    
}
