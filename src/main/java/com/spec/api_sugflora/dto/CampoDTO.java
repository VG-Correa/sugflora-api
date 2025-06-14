package com.spec.api_sugflora.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spec.api_sugflora.model.Campo;
import com.spec.speedspring.core.dto.DTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CampoDTO extends IntDomainDTO implements DTO<Campo> {

    private UsuarioDTO responsavel;
    private ProjetoDTO projeto;
    private String nome;
    private String descricao;
    private String endereco;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_inicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data_inicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data_termino;

    public CampoDTO(Campo campo) {
        initBy(campo);
    }

    @Override
    public Class<Campo> getModelClass() {
        return Campo.class;
    }

}