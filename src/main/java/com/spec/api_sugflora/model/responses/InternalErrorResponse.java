package com.spec.api_sugflora.model.responses;

public class InternalErrorResponse extends GenericResponse {

    public InternalErrorResponse(Exception e) {
        System.out.println("InternalError");
        System.out.println(e.getMessage());

        setStatus(500);
        setMessage("Erro interno inesperado");
        setError(true);
    }

}
