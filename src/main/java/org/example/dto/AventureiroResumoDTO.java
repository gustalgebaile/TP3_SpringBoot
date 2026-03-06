package org.example.dto;

import org.example.enums.ClasseAventureiro;

public record AventureiroResumoDTO(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        Boolean ativo
) {}

