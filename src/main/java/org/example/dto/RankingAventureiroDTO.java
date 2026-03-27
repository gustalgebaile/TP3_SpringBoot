package org.example.dto;

import java.math.BigDecimal;

public record RankingAventureiroDTO(
        String nomeAventureiro,
        Long totalParticipacoes,
        BigDecimal totalOuro,
        Long totalMvps
) {}