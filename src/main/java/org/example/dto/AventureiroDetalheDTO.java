package org.example.dto;

import org.example.enums.ClasseAventureiro;

public record AventureiroDetalheDTO(Long id, String nome, ClasseAventureiro classe, Integer nivel, Boolean ativo, CompanheiroDTO companheiro) {}

