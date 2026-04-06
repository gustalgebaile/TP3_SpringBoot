package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Immutable // Protege a view contra operações acidentais de INSERT/UPDATE/DELETE
@Table(name = "mv_painel_tatico_missao", schema = "operacoes")
@Getter
public class MvPainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "status")
    private String status;

    @Column(name = "nivel_perigo")
    private String nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Long totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps")
    private Long totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Long participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private OffsetDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private BigDecimal indiceProntidao;
}