package com.spec.api_sugflora.model.interfaces;

import java.lang.reflect.Field;

public interface DTOConvertable<T> {

    T toDTO();
    void InitByDTO(T dto);

    default public T mapTo (Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            
            Field[] fieldsModel = this.getClass().getDeclaredFields();

            for (Field field : fieldsModel) {
                field.setAccessible(true);

                if (DTOConvertable.class.isAssignableFrom(field.getType())) {
                    
                    DTOConvertable<?> value = (DTOConvertable<?>) field.get(this);
                    

                }


            }


        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }
}
