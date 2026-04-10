package org.example.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
@Getter
@Setter
@NoArgsConstructor
public class PainelTatico {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMissao status;

    @Column(name = "nivel_perigo", nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelPerigo nivelPerigo;

    @Column(name = "organizacao_id", nullable = false)
    private Long organizacaoId;

    @Column(name = "total_participantes", nullable = false)
    private Long totalParticipantes;

    @Column(name = "nivel_medio_equipe", nullable = false)
    private BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa", nullable = false)
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps", nullable = false)
    private Long totalMvps;

    @Column(name = "participantes_com_companheiro", nullable = false)
    private Long participantesComCompanheiro;

    @Column(name = "ultima_atualizacao", nullable = false)
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao", nullable = false)
    private BigDecimal indiceProntidao;

}