package com.spec.api_sugflora.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.exceptions.EntityAlreadActiveException;
import com.spec.api_sugflora.exceptions.EntityAlreadExistsException;
import com.spec.api_sugflora.exceptions.EntityAlreadyDeletedException;
import com.spec.api_sugflora.exceptions.EntityInvalidException;
import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;
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

    public ResponseEntity<GenericResponse> getResponseCreated(String message, Object data, Object metadata) {
        response.setStatus(201)
                .setError(false)
                .setData(data)
                .setMessage(message)
                .setMetadata(metadata);

        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseDeleted(String message, Object beckup) {
        response.setStatus(200)
                .setError(false)
                .setMessage(message);
        response.setMetadata(Map.of("beckup", beckup));

        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseCreated(String message, Object data) {
        response.setStatus(201)
                .setError(false)
                .setData(data)
                .setMessage(message);

        return getResponse();
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

    public ResponseEntity<GenericResponse> getResponseInternalError(Exception e) {
        response = new InternalErrorResponse(e);
        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseInternalError() {
        response = new InternalErrorResponse(new Exception("Erro interno do servidor"));
        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseEntityExistsException(EntityAlreadExistsException e) {
        response.setStatus(409)
                .setError(true)
                .setMessage(e.getMessage());

        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseEntityAlreadyDeletedException(EntityAlreadyDeletedException e) {
        response.setStatus(409)
                .setError(true)
                .setMessage(e.getMessage());

        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseEntityAlreadyActiveException(EntityAlreadActiveException e) {

        response.setStatus(400)
            .setError(true)
            .setMessage(e.getMessage());

        return getResponse();

    }

    public ResponseEntity<GenericResponse> getResponseNotFound(EntityNotFoundException e) {
        response.setStatus(404)
                .setError(true)
                .setMessage(e.getMessage());

        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseInvalidEntity(EntityInvalidException e) {
        response.setStatus(400)
                .setError(true)
                .setMessage(e.getMessage());

        return getResponse();
    }

    public ResponseEntity<GenericResponse> getResponseException(Exception e) {

        if (e instanceof EntityAlreadExistsException) {
            return getResponseEntityExistsException((EntityAlreadExistsException)e);
        } else
        if (e instanceof EntityAlreadyDeletedException) {
            return getResponseEntityAlreadyDeletedException((EntityAlreadyDeletedException)e);
        } else 
        if (e instanceof EntityNotFoundException) {
            return getResponseNotFound((EntityNotFoundException)e);
        } else 
        if (e instanceof EntityInvalidException) {
            return getResponseInvalidEntity((EntityInvalidException)e);
        } else 
        if (e instanceof EntityAlreadActiveException) {
            return getResponseEntityAlreadyActiveException((EntityAlreadActiveException)e);
        }

        else {
            return getResponseInternalError(e);
        }

    }

    public <R> List<R> toDTO(List<? extends DTOConvertable<?, R>> dtoConvertable) {

        return dtoConvertable.stream().map(conertable -> (R) conertable.toDTO()).collect(Collectors.toList());

    }

}
