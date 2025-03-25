package com.spec.api_sugflora.model.responses;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class NotFoundResponse extends GenericResponse{

    public NotFoundResponse(Exception e) {
        setMessage(e.getMessage());
        setData(new ArrayList<>());
        setStatus(404);
        setError(true);
    }

}
