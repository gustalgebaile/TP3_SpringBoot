package org.example.repository;

import org.example.dto.RankingAventureiroDTO;
import org.example.model.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {

    @Query("SELECT COUNT(p) FROM ParticipacaoMissao p WHERE p.aventureiro.id = :aventureiroId")
    Long contarParticipacoesPorAventureiro(@Param("aventureiroId") Long aventureiroId);

    @Query("SELECT p FROM ParticipacaoMissao p WHERE p.aventureiro.id = :aventureiroId ORDER BY p.dataRegistro DESC LIMIT 1")
    Optional<ParticipacaoMissao> buscarUltimaParticipacaoPorAventureiro(@Param("aventureiroId") Long aventureiroId);

    List<ParticipacaoMissao> findByMissaoId(Long missaoId);

    @Query("SELECT new org.example.dto.RankingAventureiroDTO(" +
            "a.nome, " +
            "COUNT(p), " +
            "COALESCE(SUM(p.recompensaOuro), 0), " +
            "SUM(CASE WHEN p.destaqueMvp = true THEN 1 ELSE 0 END)) " +
            "FROM ParticipacaoMissao p " +
            "JOIN p.aventureiro a " +
            "JOIN p.missao m " +
            "WHERE (cast(:dataInicio as timestamp) IS NULL OR m.dataInicio >= :dataInicio) " +
            "AND (cast(:dataFim as timestamp) IS NULL OR m.dataTermino <= :dataFim) " +
            "AND (:status IS NULL OR m.status = :status) " +
            "GROUP BY a.id, a.nome " +
            "ORDER BY COUNT(p) DESC, COALESCE(SUM(p.recompensaOuro), 0) DESC")
    List<RankingAventureiroDTO> gerarRankingAventureiros(
            @Param("dataInicio") java.time.OffsetDateTime dataInicio,
            @Param("dataFim") java.time.OffsetDateTime dataFim,
            @Param("status") org.example.enums.StatusMissao status
    );
}