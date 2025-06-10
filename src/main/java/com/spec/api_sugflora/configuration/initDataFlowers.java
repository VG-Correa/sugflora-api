package com.spec.api_sugflora.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.FamiliaWriteDTO;
import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.NomePopular;
import com.spec.api_sugflora.service.EspecieService;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class initDataFlowers {

    private final FamiliaService familiaService;
    private final GeneroService generoService;
    private final EspecieService especieService;

    private void setFlowers(){

        Familia familia = new Familia();
        familia.setNome("Nome de Família");
        familia.setDescricao("Descrição");

        Genero genero = new Genero();
        genero.setNome("Nome do Gênero");
        genero.setDescricao("Descrição");
        genero.setFamilia(familia);
        
        Especie especie = new Especie();
        especie.setNome("Nome da espécie");
        especie.setDescricao("Descrição");
        especie.setGenero(genero);
        especie.setFamilia(familia);

        List<NomePopular> nomePopulares = new ArrayList<>();
        nomePopulares.add(new NomePopular("Nome popular"));
        nomePopulares.add(new NomePopular("Nome popular 2"));
        nomePopulares.add(new NomePopular("Nome popular 3"));

        especie.setNomesPopulares(nomePopulares);




    }

}
