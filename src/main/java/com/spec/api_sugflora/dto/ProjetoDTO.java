package com.spec.api_sugflora.dto;

import org.springframework.web.multipart.MultipartFile;

import com.spec.api_sugflora.model.Projeto;
import com.spec.speedspring.core.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO extends IntDomainDTO implements DTO<Projeto> {
    private String nome;
    private String descricao;
    private UsuarioDTO dono;
    private boolean isPublic;

    public ProjetoDTO(Projeto projeto) {
        this.initBy(projeto);
    }

    @Override
    public Class<Projeto> getModelClass() {
        return Projeto.class;
    }
}
