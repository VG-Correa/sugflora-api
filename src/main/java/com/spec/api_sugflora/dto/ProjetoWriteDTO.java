package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoWriteDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private UUID usuario_dono_uuid;
    private boolean isPublic;
    private LocalDateTime inicio;
    private LocalDateTime termino;

}
