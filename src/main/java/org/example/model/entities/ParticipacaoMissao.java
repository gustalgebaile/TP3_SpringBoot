package org.example.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.PapelMissao;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "participacoes_missao",
        schema = "aventura",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_participacao_unica", columnNames = {"missao_id", "aventureiro_id"})
        }
)
@Getter
@Setter
public class ParticipacaoMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_na_missao", nullable = false)
    private PapelMissao papelNaMissao;

    @Min(value = 0, message = "A recompensa em ouro não pode ser negativa")
    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMvp;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private OffsetDateTime dataRegistro;
    @PrePersist
    public void prePersist() {
        this.dataRegistro = OffsetDateTime.now();
        if (this.destaqueMvp == null) {
            this.destaqueMvp = false;
        }
    }
}