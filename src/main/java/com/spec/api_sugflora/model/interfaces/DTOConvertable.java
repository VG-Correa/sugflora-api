package com.spec.api_sugflora.model.interfaces;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public interface DTOConvertable<T> {
    void InitByDTO(T dto);

    Class<T> getDTOClass();

    default T toDTO() {
        try {
            Class<T> dtoClass = getDTOClass();
            Constructor<T> constructor = dtoClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T dto = constructor.newInstance();
            mapPropertiesUsingReflection(this, dto);
            
            try {
                Field fieldClassModel = dtoClass.getDeclaredField("modelClass");    
                fieldClassModel.setAccessible(true);
                fieldClassModel.set(dto, null);
                System.err.println("localizou modelClass");
            } catch (Exception e) {
                System.err.println("Não localizou modelClass");
                // Não faz nada

            }

            return dto;
        } catch (Exception e) {
            System.err.println("Erro ao instanciar ou mapear dto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    default T toDTO(Class<T> dtoClass) {
        try {
            Constructor<T> constructor = dtoClass.getDeclaredConstructor();
            constructor.setAccessible(true); 
            T dto = constructor.newInstance();
            mapPropertiesUsingReflection(this, dto);

            try {
                Field fieldClassModel = dtoClass.getDeclaredField("modelClass");
                fieldClassModel.setAccessible(true);
                fieldClassModel.set(dto, null);
                System.err.println("localizou modelClass");
            } catch (Exception e) {
                System.err.println("Não localizou modelClass");
                // Não faz nada
            }

            return dto;
        } catch (Exception e) {
            System.err.println("Erro ao instanciar ou mapear dto: " + e.getMessage());
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
                // Ignorar campos estáticos, finais ou com nome "modelClass"
                if (Modifier.isStatic(sourceField.getModifiers()) || 
                    Modifier.isFinal(sourceField.getModifiers()) ||
                    sourceField.getName().equals("modelClass")) {
                    continue;
                }
                
                // Torna o campo fonte acessível
                sourceField.setAccessible(true);
                String fieldName = sourceField.getName();
                
                try {
                    // Tenta encontrar campo correspondente no target
                    Field targetField = findField(target.getClass(), fieldName);
                    
                    // Ignora se o campo se chamar "modelClass"
                    if (targetField.getName().equals("modelClass")) {
                        continue;
                    }
                    
                    // Torna o campo alvo acessível
                    targetField.setAccessible(true);
                    
                    // Verifica compatibilidade de tipos
                    if (targetField.getType().isAssignableFrom(sourceField.getType())) {
                        Object value = sourceField.get(source);
                        targetField.set(target, value);
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
        // Ignorar busca pelo campo "modelClass"
        if (fieldName.equals("modelClass")) {
            throw new NoSuchFieldException("Campo 'modelClass' ignorado propositalmente");
        }
        
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
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
