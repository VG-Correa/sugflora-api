package com.spec.api_sugflora.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecieWriteDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Integer genero_id;
}
