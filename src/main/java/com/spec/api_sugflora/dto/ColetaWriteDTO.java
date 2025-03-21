package com.spec.api_sugflora.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ColetaWriteDTO {

    private Integer projeto_id;
    private Integer campo_id;
    private UUID responsavel_id;
    private LocalDate data_coleta;

    private Integer familia_id = null;
    private Integer genero_id = null;
    private Integer especie_id = null;
    private String coletor;

}
