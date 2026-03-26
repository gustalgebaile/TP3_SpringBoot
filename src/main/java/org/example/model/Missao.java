package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import org.example.model.audit.Organizacao;

import java.time.OffsetDateTime;

@Entity
@Table(name = "missoes", schema = "aventura")
@Getter
@Setter
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false)
    private NivelPerigo nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMissao status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_termino")
    private OffsetDateTime dataTermino;

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        // Por defeito, uma nova missão começa como PLANEJADA se não for especificado
        if (this.status == null) {
            this.status = StatusMissao.PLANEJADA;
        }
    }
}