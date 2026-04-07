package org.example;

import org.example.dto.*;
import org.example.enums.ClasseAventureiro;
import org.example.enums.NivelPerigo;
import org.example.enums.PapelMissao;
import org.example.enums.StatusMissao;
import org.example.model.adventure.Aventureiro;
import org.example.model.adventure.Missao;
import org.example.model.adventure.ParticipacaoMissao;
import org.example.model.audit.Organizacao;
import org.example.model.audit.Usuario;
import org.example.repository.*;
import org.example.service.AventureiroService;
import org.example.service.MissaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RelatoriosIntegrationTest {

    @Autowired private ParticipacaoMissaoRepository participacaoRepo;
    @Autowired private MissaoRepository missaoRepo;
    @Autowired private AventureiroRepository aventureiroRepo;
    @Autowired private OrganizacaoRepository orgRepo;
    @Autowired private UsuarioRepository userRepo;

    @Autowired private AventureiroService aventureiroService;
    @Autowired private MissaoService missaoService;

    private Long aventureiroId;
    private Long missaoId;

    @BeforeEach
    void setup() {
        Organizacao org = new Organizacao();
        org.setNome("Guilda de Teste Relatório " + System.currentTimeMillis());
        org.setAtivo(true);
        org.setCreatedAt(OffsetDateTime.now());
        org = orgRepo.save(org);

        Usuario user = new Usuario();
        user.setNome("Admin");
        user.setEmail("admin" + System.currentTimeMillis() + "@teste.com");
        user.setSenhaHash("123");
        user.setStatus("ATIVO");
        user.setOrganizacao(org);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user = userRepo.save(user);

        Aventureiro a1 = new Aventureiro();
        a1.setNome("Geralt");
        a1.setClasse(ClasseAventureiro.GUERREIRO);
        a1.setNivel(30);
        a1.setAtivo(true);
        a1.setOrganizacao(org);
        a1.setUsuarioResponsavel(user);
        a1 = aventureiroRepo.save(a1);
        this.aventureiroId = a1.getId();

        Missao m1 = new Missao();
        m1.setTitulo("Caçar Grifo");
        m1.setStatus(StatusMissao.CONCLUIDA);
        m1.setNivelPerigo(NivelPerigo.RANK_B);
        m1.setOrganizacao(org);
        m1 = missaoRepo.save(m1);
        this.missaoId = m1.getId();

        ParticipacaoMissao p1 = new ParticipacaoMissao();
        p1.setMissao(m1);
        p1.setAventureiro(a1);
        p1.setPapelNaMissao(PapelMissao.COMBATENTE);
        p1.setRecompensaOuro(new BigDecimal("150.50"));
        p1.setDestaqueMvp(true);
        participacaoRepo.save(p1);
    }

    // REQUISITO: Listagens com filtro
    @Test
    void deveBuscarAventureiroPorNomeParcial() {
        PageResult<AventureiroResumoDTO> resultado = aventureiroService.buscarPorNome("geral", 0, 10);

        assertThat(resultado.content()).hasSize(1);
        assertThat(resultado.content().get(0).nome()).isEqualTo("Geralt");
    }

    // REQUISITO: Consulta detalhada (Cenário 1)
    @Test
    void deveBuscarPerfilCompletoDoAventureiro() {
        AventureiroPerfilDTO perfil = aventureiroService.buscarPerfilCompleto(aventureiroId);

        assertThat(perfil.nome()).isEqualTo("Geralt");
        assertThat(perfil.totalParticipacoes()).isEqualTo(1L);
        assertThat(perfil.tituloUltimaMissao()).isEqualTo("Caçar Grifo");
    }

    // REQUISITO: Consulta detalhada (Cenário 2)
    @Test
    void deveBuscarDetalhesDaMissaoComParticipantes() {
        MissaoDetalheDTO detalhes = missaoService.buscarDetalhes(missaoId);

        assertThat(detalhes.titulo()).isEqualTo("Caçar Grifo");
        assertThat(detalhes.participantes()).hasSize(1);
        assertThat(detalhes.participantes().get(0).nomeAventureiro()).isEqualTo("Geralt");
        assertThat(detalhes.participantes().get(0).recompensa()).isEqualByComparingTo("150.50");
    }

    // REQUISITO: Relatórios agregados
    @Test
    void deveGerarMetricasDeMissaoSemDuplicidades() {
        List<RelatorioMissaoDTO> metricas = missaoRepo.gerarRelatorioMissoes(null, null);

        assertThat(metricas).isNotEmpty();
        RelatorioMissaoDTO missao = metricas.get(0);
        assertThat(missao.titulo()).isEqualTo("Caçar Grifo");
        assertThat(missao.quantidadeParticipantes()).isEqualTo(1L);
        assertThat(missao.totalOuroDistribuido()).isEqualByComparingTo("150.50");
    }

    // REQUISITO: Ranking
    @Test
    void deveGerarRankingComTotaisCorretos() {
        List<RankingAventureiroDTO> ranking = participacaoRepo.gerarRankingAventureiros(null, null, null);

        assertThat(ranking).isNotEmpty();
        RankingAventureiroDTO geralt = ranking.get(0);
        assertThat(geralt.nomeAventureiro()).isEqualTo("Geralt");
        assertThat(geralt.totalParticipacoes()).isEqualTo(1L);
        assertThat(geralt.totalOuro()).isEqualByComparingTo("150.50");
        assertThat(geralt.totalMvps()).isEqualTo(1L);
    }
}