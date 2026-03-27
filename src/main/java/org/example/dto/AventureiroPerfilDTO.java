package org.example.dto;

import org.example.enums.ClasseAventureiro;

public record AventureiroPerfilDTO(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        Boolean ativo,
        CompanheiroDTO companheiro, // Pode ser null
        Long totalParticipacoes,    // Agregação (Count)
        String tituloUltimaMissao   // Pode ser null
) {}