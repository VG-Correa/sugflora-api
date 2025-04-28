package com.spec.api_sugflora.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.dto.FamiliaDTO;
import com.spec.api_sugflora.dto.FamiliaWriteDTO;
import com.spec.speedspring.core.dtoConvertable.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Familia extends IntDomain implements DTOConvertable<FamiliaWriteDTO, FamiliaDTO> {

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = true, unique = false)
    private String descricao;

    @OneToMany(mappedBy = "familia")
    @JsonIgnore
    public List<Genero> generos = new ArrayList<>();

    public Familia(FamiliaWriteDTO familiaWriteDTO) {
        this.initBy(familiaWriteDTO);
    }

    @Override
    public Class<FamiliaDTO> getDTOClass() {
        return FamiliaDTO.class;
    }

    public void isValid() {
        if (this.nome == null || this.nome.isEmpty()) {
            throw new IllegalArgumentException("Nome da familia não pode ser nulo ou vazio");
        }
    }

    @Override
    public boolean getLog() {
        return false;
    }

}
