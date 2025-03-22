package com.spec.api_sugflora.model;

import java.util.ArrayList;
import java.util.List;

import com.spec.api_sugflora.dto.EspecieDTO;
import com.spec.api_sugflora.dto.EspecieWriteDTO;
import com.spec.api_sugflora.dto.GeneroDTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Especie extends IntDomain implements DTOConvertable<EspecieWriteDTO, EspecieDTO> {

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = true, unique = true)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "especie_id")
    private List<NomePopular> nomesPopulares = new ArrayList<>();

    @Override
    public Class<EspecieDTO> getDTOClass() {
        return EspecieDTO.class;
    }

}
