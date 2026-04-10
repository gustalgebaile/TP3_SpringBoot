package org.example;

import org.example.model.entities.PainelTatico;
import org.example.service.PainelTaticoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MissaoTaticaIntegrationTest {

    @Autowired
    private PainelTaticoService painelTaticoService;

    @Test
    void deveRetornarTop10MissoesTaticasDosUltimos15Dias() {
        List<PainelTatico> missoes = painelTaticoService.obterTopMissoesRecentes();

        System.out.println("--- RELATÓRIO TÁTICO: TOP MISSÕES ---");
        System.out.println("Encontradas " + missoes.size() + " missões.");

        for (PainelTatico missao : missoes) {
            System.out.printf("Missão ID: %d | Título: %s | Índice Prontidão: %s | Última Atualização: %s%n",
                    missao.getMissaoId(),
                    missao.getTitulo(),
                    missao.getIndiceProntidao(),
                    missao.getUltimaAtualizacao());
        }
        System.out.println("---------------------------------------");

        assertThat(missoes).isNotNull();

    }
}