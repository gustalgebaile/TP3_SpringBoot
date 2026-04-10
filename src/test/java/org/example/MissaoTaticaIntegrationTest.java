package org.example;

import org.example.model.entities.MvPainelTaticoMissao;
import org.example.service.PainelTaticoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MissaoTaticaIntegrationTest {

    @Autowired
    private PainelTaticoService painelTaticoService;

    @Test
    void deveRetornarTop10MissoesTaticasDosUltimos15Dias() {
        List<MvPainelTaticoMissao> missoes = painelTaticoService.obterTopMissoesRecentes();

        System.out.println("--- RELATÓRIO TÁTICO: TOP MISSÕES ---");
        System.out.println("Encontradas " + missoes.size() + " missões táticas nos últimos 15 dias.");

        for (MvPainelTaticoMissao missao : missoes) {
            System.out.printf("Missão ID: %d | Título: %s | Índice Prontidão: %s | Última Atualização: %s%n",
                    missao.getMissaoId(),
                    missao.getTitulo(),
                    missao.getIndiceProntidao(),
                    missao.getUltimaAtualizacao());
        }
        System.out.println("---------------------------------------");

        assertThat(missoes).isNotNull();
        assertThat(missoes.size()).isLessThanOrEqualTo(10);

        if (!missoes.isEmpty()) {
            OffsetDateTime dataLimite = OffsetDateTime.now().minusDays(15);

            // 1. Valida se todas as missões retornadas são realmente dos últimos 15 dias
            assertThat(missoes).allMatch(missao ->
                    missao.getUltimaAtualizacao().isAfter(dataLimite) ||
                            missao.getUltimaAtualizacao().isEqual(dataLimite)
            );

            // 2. Valida se a ordenação está correta (Decrescente por Índice de Prontidão)
            for (int i = 0; i < missoes.size() - 1; i++) {
                assertThat(missoes.get(i).getIndiceProntidao())
                        .isGreaterThanOrEqualTo(missoes.get(i + 1).getIndiceProntidao());
            }
        }
    }
}