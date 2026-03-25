package org.example.model.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
public class Organizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private Boolean ativo;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}
