package com.spec.api_sugflora.dto;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    
    @JsonIgnore
    private UsuarioDTO dono;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate previsaoConclusao;

    public ProjetoDTO(Projeto projeto) {
        this.initBy(projeto);
    }

    @Override
    public Class<Projeto> getModelClass() {
        return Projeto.class;
    }
}
