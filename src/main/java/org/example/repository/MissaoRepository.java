package org.example.repository;

import org.example.dto.RelatorioMissaoDTO;
import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import org.example.model.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @Query("SELECT m FROM Missao m WHERE " +
            "(:status IS NULL OR m.status = :status) AND " +
            "(:nivel IS NULL OR m.nivelPerigo = :nivel) AND " +
            "(cast(:dataInicio as timestamp) IS NULL OR m.dataInicio >= :dataInicio) AND " +
            "(cast(:dataFim as timestamp) IS NULL OR m.dataTermino <= :dataFim)")
    Page<Missao> buscarFiltrado(
            @Param("status") StatusMissao status,
            @Param("nivel") NivelPerigo nivel,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            Pageable pageable);

    @Query("SELECT new org.example.dto.RelatorioMissaoDTO(" +
            "m.titulo, " +
            "m.status, " +
            "m.nivelPerigo, " +
            "COUNT(p), " +
            "COALESCE(SUM(p.recompensaOuro), 0)) " +
            "FROM Missao m " +
            "LEFT JOIN ParticipacaoMissao p ON p.missao = m " +
            "WHERE (cast(:dataInicio as timestamp) IS NULL OR m.dataInicio >= :dataInicio) " +
            "AND (cast(:dataFim as timestamp) IS NULL OR m.dataTermino <= :dataFim) " +
            "GROUP BY m.id, m.titulo, m.status, m.nivelPerigo " +
            "ORDER BY m.dataInicio DESC")
    List<RelatorioMissaoDTO> gerarRelatorioMissoes(
            @Param("dataInicio") java.time.OffsetDateTime dataInicio,
            @Param("dataFim") java.time.OffsetDateTime dataFim
    );
}
