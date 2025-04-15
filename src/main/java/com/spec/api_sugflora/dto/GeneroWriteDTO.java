package com.spec.api_sugflora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneroWriteDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Integer familia_id;

}
