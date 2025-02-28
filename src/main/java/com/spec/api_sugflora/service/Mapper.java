package com.spec.api_sugflora.service;

import java.lang.reflect.Field;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

@Service
public class Mapper {

    public static Object mapToDTO(DTOConvertable model, Class<DTO> dtoClass) {
        
        try {

            for (Field field : model.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(model);

                if (value != null && DTOConvertable.class.isAssignableFrom(value.getClass())) {
                    
                } 
                
                Field dtoField = dtoClass.getDeclaredField(field.getName());




            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
        return null;
    }

}
