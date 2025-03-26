package com.spec.api_sugflora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.model.responses.InternalErrorResponse;

import jakarta.persistence.EntityExistsException;

@RestController
public class GenericRestController {

    @Autowired
    public GenericResponse response;

    public ResponseEntity<GenericResponse> getResponse() {
        return ResponseEntity.status(response.getStatus()).body(response.build());
    }

    public ResponseEntity<GenericResponse> getResponseOK(String message, Object data, Object metadata) {
        response.setStatus(200)
            .setError(false)
            .setData(data)
            .setMessage(message)
            .setMetadata(metadata);
        
            return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseOK(String message) {
        response.setStatus(200)
            .setError(false)
            .setMessage(message);
        
            return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseOK(String message, Object data) {
        response.setStatus(200)
            .setError(false)
            .setData(data)
            .setMessage(message);
        
            return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseInternalError(Exception e){
        response = new InternalErrorResponse(e);
        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseInternalError(){
        response = new InternalErrorResponse(new Exception("Erro interno do servidor"));
        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseEntityExistsException(EntityExistsException e) {
        response.setStatus(409)
            .setError(true)
            .setMessage(e.getMessage());
        
        return getResponse();
    }
    
    public ResponseEntity<GenericResponse> getResponseNotFound(String message) {
        response.setStatus(404)
            .setError(true)
            .setMessage(message);
        
            return getResponse();
    }


}
