package com.spec.api_sugflora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliaWriteDTO {
    private Integer id;
    private String nome;
    private String descricao;
}
