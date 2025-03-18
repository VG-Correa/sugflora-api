package com.spec.api_sugflora.dto;

import java.util.UUID;

import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoWriteDTO {

    private String nome;
    private String descricao;
    private UUID usuario_dono_uuid;

}
