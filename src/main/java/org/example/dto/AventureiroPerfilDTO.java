package org.example.dto;

import org.example.enums.ClasseAventureiro;

public record AventureiroPerfilDTO(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        Boolean ativo,
        CompanheiroDTO companheiro,
        Long totalParticipacoes,
        String tituloUltimaMissao
) {}