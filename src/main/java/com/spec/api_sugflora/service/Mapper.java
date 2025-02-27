package com.spec.api_sugflora.service;

import java.lang.reflect.Field;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class Mapper {

    public static <T, D> D mapToDTO(T model, Class<D> dtoClass) {
        
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();

            for (Field field : model.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Field dtoField = dtoClass.getDeclaredField(field.getName());



            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
        return null;
    }

}
