package org.example.dto;

import org.example.enums.PapelMissao;
import java.math.BigDecimal;

public record ParticipanteMissaoDTO(
        Long aventureiroId,
        String nomeAventureiro,
        PapelMissao papel,
        BigDecimal recompensa,
        Boolean destaqueMvp
) {}