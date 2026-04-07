package org.example.repository;

import org.example.model.adventure.MvPainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MvPainelTaticoMissaoRepository extends JpaRepository<MvPainelTaticoMissao, Long> {

    List<MvPainelTaticoMissao> findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(OffsetDateTime dataLimite);
}