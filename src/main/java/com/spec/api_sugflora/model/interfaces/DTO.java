package com.spec.api_sugflora.model.interfaces;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public interface DTO<T> {
    void initByModel(T model);

    Class<T> getModelClass();

    default T toModel() {
        try {
            Class<T> modelClass = getModelClass();
            Constructor<T> constructor = modelClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T model = constructor.newInstance();            
            mapPropertiesUsingReflection(this, model);
            
            try {
                Field fieldClassModel = modelClass.getDeclaredField("modelClass");
                fieldClassModel.setAccessible(true);
                fieldClassModel.set(model, null);
            } catch (Exception e) {
                // Não faz nada
            }

            return model;
        } catch (Exception e) {
            System.err.println("Erro ao instanciar ou mapear model: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    default T toModel(Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T model = constructor.newInstance();            
            mapPropertiesUsingReflection(this, model);

            try {
                Field fieldClassModel = modelClass.getDeclaredField("modelClass");
                fieldClassModel.setAccessible(true);
                fieldClassModel.set(model, null);
            } catch (Exception e) {
                // Não faz nada
            }

            return model;
        } catch (Exception e) {
            System.err.println("Erro ao instanciar ou mapear model: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    default void mapPropertiesUsingReflection(Object source, T target) throws Exception {
        // Mapeia campos da classe atual
        mapFieldsFromClass(source, target, source.getClass());
        
        // Mapeia também campos das superclasses
        Class<?> superClass = source.getClass().getSuperclass();
        while (superClass != null && superClass != Object.class) {
            mapFieldsFromClass(source, target, superClass);
            superClass = superClass.getSuperclass();
        }
    }
    
    default void mapFieldsFromClass(Object source, T target, Class<?> sourceClass) throws Exception {
        Field[] sourceFields = sourceClass.getDeclaredFields();
        
        for (Field sourceField : sourceFields) {
            try {
                // Torna o campo fonte acessível
                sourceField.setAccessible(true);
                String fieldName = sourceField.getName();
                
                try {
                    if (!fieldName.equals("modelClass")) {
                    
                        // Tenta encontrar campo correspondente no target
                        Field targetField = findField(target.getClass(), fieldName);
                        
                        // Torna o campo alvo acessível
                        targetField.setAccessible(true);
                        
                        // Verifica compatibilidade de tipos
                        if (targetField.getType().isAssignableFrom(sourceField.getType())) {
                            Object value = sourceField.get(source);
                            targetField.set(target, value);
                        }
                    }
                } catch (NoSuchFieldException e) {
                    // Campo não existe no DTO, ignora silenciosamente
                } catch (IllegalAccessException e) {
                    System.out.println("Aviso: Não foi possível acessar o campo " + fieldName + " - " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar campo: " + e.getMessage());
            }
        }
    }
    
    default Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true); // Torna o campo acessível
            return field;
        } catch (NoSuchFieldException e) {
            // Se não encontrou na classe atual, verifica na superclasse
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return findField(superClass, fieldName);
            }
            throw e;
        }
    }
    
    
}
