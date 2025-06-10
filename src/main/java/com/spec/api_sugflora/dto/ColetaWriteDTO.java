package com.spec.api_sugflora.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ColetaWriteDTO {
    private Integer id;

    private Integer campo_id;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_coleta;

    private Integer familia_id = null;
    private Integer genero_id = null;
    private Integer especie_id = null;
    // private String observacao;

}
