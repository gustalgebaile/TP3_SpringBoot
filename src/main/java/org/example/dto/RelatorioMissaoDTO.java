package org.example.dto;

import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import java.math.BigDecimal;

public record RelatorioMissaoDTO(
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        Long quantidadeParticipantes,
        BigDecimal totalOuroDistribuido
) {}