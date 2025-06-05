package com.spec.api_sugflora.model.responses;

import com.spec.api_sugflora.dto.UsuarioDTO;

import lombok.Data;

@Data
public class tokenResponse {
    private String token;
    private UsuarioDTO usuario;
}
