package com.spec.api_sugflora.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampoWriteDTO {
    private Integer id;
    private UUID usuario_responsavel_uuid;
    private Integer projeto_id;
    private String nome;
    private String descricao;
    private String endereco;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
}
