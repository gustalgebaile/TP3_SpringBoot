package org.example.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.EspecieCompanheiro;

@Entity
@Table(name = "companheiros", schema = "aventura")
@Getter
@Setter
public class Companheiro {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EspecieCompanheiro especie;

    @Column(nullable = false)
    private Integer lealdade;

    public Companheiro() {}

    public Companheiro(String nome, EspecieCompanheiro especie, Integer lealdade) {
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }
}
