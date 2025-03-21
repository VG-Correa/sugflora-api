package com.spec.api_sugflora.model.interfaces;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DTOConvertable<W, R> {

    @JsonIgnore
    Class<R> getDTOClass();

    default void initBy(W classe) {

        Field[] fields = classe.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Field targetField = findField(getClass(), fieldName);
                targetField.setAccessible(true);

                if (targetField.getType().isAssignableFrom(field.getType())) {
                    Object value = field.get(classe);
                    targetField.set(this, value);
                }
                
            } catch (NoSuchFieldException e) {
                // pass
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    default R toDTO() {
        try {
            Class<R> dtoClass = getDTOClass();
            Constructor<R> constructor = dtoClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            R dto = constructor.newInstance();
            mapPropertiesUsingReflection(this, dto);

            return dto;
        } catch (Exception e) {
            System.err.println("Erro ao instanciar ou mapear dto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    default R toDTO(Class<R> dtoClass) {
        try {
            Constructor<R> constructor = dtoClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            R dto = constructor.newInstance();
            mapPropertiesUsingReflection(this, dto);

            return dto;
        } catch (Exception e) {
            System.err.println("Erro ao instanciar ou mapear dto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    default void mapPropertiesUsingReflection(Object source, R target) throws Exception {
        // Mapeia campos da classe atual
        mapFieldsFromClass(source, target, source.getClass());

        // Mapeia também campos das superclasses
        Class<?> superClass = source.getClass().getSuperclass();
        while (superClass != null && superClass != Object.class) {
            mapFieldsFromClass(source, target, superClass);
            superClass = superClass.getSuperclass();
        }
    }

    default void mapFieldsFromClass(Object source, R target, Class<?> sourceClass) throws Exception {
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
