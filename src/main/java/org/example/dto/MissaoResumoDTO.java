package org.example.dto;

import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import java.time.OffsetDateTime;

public record MissaoResumoDTO(
        Long id,
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        OffsetDateTime dataInicio,
        OffsetDateTime dataTermino
) {}