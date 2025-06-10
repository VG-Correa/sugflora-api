package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class ProjetoWriteDTO {
    private Integer id;
    private String nome;
    private LocalDateTime inicio;
    private String descricao;
    private LocalDateTime previsaoConclusao;
    private UUID usuario_dono_uuid;
}
