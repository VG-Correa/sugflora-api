package com.spec.api_sugflora.dto;

import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ProjetoWriteDTO {
    private Integer id;
    private String nome;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;
    
    private String descricao;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate previsaoConclusao;
    
    private UUID usuario_dono_uuid;
}
