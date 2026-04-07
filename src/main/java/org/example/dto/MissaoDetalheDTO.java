package org.example.dto;

import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import java.time.OffsetDateTime;
import java.util.List;

public record MissaoDetalheDTO(
        Long id,
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        OffsetDateTime dataInicio,
        OffsetDateTime dataTermino,
        List<ParticipanteMissaoDTO> participantes
) {}