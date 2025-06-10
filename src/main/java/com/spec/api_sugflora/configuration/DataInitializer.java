package com.spec.api_sugflora.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.NomePopular;
import com.spec.api_sugflora.service.EspecieService;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;

import lombok.RequiredArgsConstructor;

/**
 * Inicializa dados de famílias, gêneros e espécies de flores ao subir a aplicação.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final FamiliaService familiaService;
    private final GeneroService generoService;
    private final EspecieService especieService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // evita duplicação caso já tenha sido inicializado
        if (familiaService.findAll().size() > 0) return;

        // Exemplo de famílias com gêneros e espécies populares
        List<FamiliaSeed> seeds = Arrays.asList(
            new FamiliaSeed("Rosaceae", "Família das rosas",
                Arrays.asList(
                    new GeneroSeed("Rosa", "Gênero das rosas verdadeiras", Arrays.asList(
                        new EspecieSeed("Rosa rubiginosa", "Rosa-brava", "Sweet briar rose", Arrays.asList("Rosa mosqueta", "Prairie rose")),
                        new EspecieSeed("Rosa gallica", "Rosa-da-Província", "French rose", Arrays.asList("Rosa-de-frança", "Rosa-antiga"))
                    )),
                    new GeneroSeed("Fragaria", "Gênero dos morangos", Arrays.asList(
                        new EspecieSeed("Fragaria × ananassa", "Morangão", "Garden strawberry", Arrays.asList("Morangão-comum", "Frutilla"))
                    ))
                )
            ),
            new FamiliaSeed("Asteraceae", "Família das margaridas",
                Arrays.asList(
                    new GeneroSeed("Helianthus", "Girasol", Arrays.asList(
                        new EspecieSeed("Helianthus annuus", "Girassol", "Sunflower", Arrays.asList("Floresol", "Girassol-comum"))
                    )),
                    new GeneroSeed("Bellis", "Margaridas", Arrays.asList(
                        new EspecieSeed("Bellis perennis", "Margarida-dos-prados", "English daisy", Arrays.asList("Margaridinha", "Prímula"))
                    ))
                )
            ),
            new FamiliaSeed("Fabaceae", "Família das leguminosas",
                Arrays.asList(
                    new GeneroSeed("Pisum", "Gênero dos ervilhos", Arrays.asList(
                        new EspecieSeed("Pisum sativum", "Ervilha-comum", "Garden pea", Arrays.asList("Ervilhaca", "Pisum"))
                    )),
                    new GeneroSeed("Phaseolus", "Gênero das favas", Arrays.asList(
                        new EspecieSeed("Phaseolus vulgaris", "Feijão-comum", "Common bean", Arrays.asList("Feijão-preto", "Feijão-carioca"))
                    ))
                )
            ),
            new FamiliaSeed("Orchidaceae", "Família das orquídeas",
                Arrays.asList(
                    new GeneroSeed("Phalaenopsis", "Orquídea borboleta", Arrays.asList(
                        new EspecieSeed("Phalaenopsis amabilis", "Orquídea-amável", "Moon orchid", Arrays.asList("Borboleta-branca", "Phalaeno"))
                    )),
                    new GeneroSeed("Cattleya", "Orquídea-cattleya", Arrays.asList(
                        new EspecieSeed("Cattleya labiata", "Orquídea-labiata", "Corsage orchid", Arrays.asList("Rainha-demarço", "Cattleya"))
                    ))
                )
            ),
            new FamiliaSeed("Poaceae", "Família das gramíneas",
                Arrays.asList(
                    new GeneroSeed("Zea", "Gênero do milho", Arrays.asList(
                        new EspecieSeed("Zea mays", "Milho", "Maize", Arrays.asList("Milho-verde", "Pipoca"))
                    )),
                    new GeneroSeed("Oryza", "Gênero do arroz", Arrays.asList(
                        new EspecieSeed("Oryza sativa", "Arroz-comum", "Rice", Arrays.asList("Arroz-branco", "Arroz-integral"))
                    ))
                )
            )
        );

        for (FamiliaSeed fs : seeds) {
            Familia fam = new Familia();
            fam.setNome(fs.familia);
            fam.setDescricao(fs.descricao);
            familiaService.save(fam);

            for (GeneroSeed gs : fs.generos) {
                Genero gen = new Genero(); gen.setNome(gs.genero); gen.setDescricao(gs.descricao); gen.setFamilia(fam);
                generoService.save(gen);

                for (EspecieSeed es : gs.especies) {
                    Especie sp = new Especie();
                    sp.setNome(es.especie);
                    sp.setDescricao(es.descricao);
                    sp.setGenero(gen);
                    sp.setFamilia(fam);
                    List<NomePopular> populares = es.populares.stream()
                        .map(NomePopular::new)
                        .toList();
                    sp.setNomesPopulares(populares);
                    especieService.save(sp);
                }
            }
        }
    }

    // classes internas para organizar seeds
    record FamiliaSeed(String familia, String descricao, List<GeneroSeed> generos) {}
    record GeneroSeed(String genero, String descricao, List<EspecieSeed> especies) {}
    record EspecieSeed(String especie, String descricao, String ingles, List<String> populares) {}
}
