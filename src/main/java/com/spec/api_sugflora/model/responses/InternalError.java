package com.spec.api_sugflora.model.responses;

public class InternalError extends GenericResponse{
    
    public InternalError() {
        setStatus(500);
        setMessage("Erro interno inesperado");
        setError(true);
    }

}
