package org.example.repository;

import org.example.model.entities.PainelTatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PainelTaticoRepository extends JpaRepository<PainelTatico, Long> {

    List<PainelTatico> findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(LocalDateTime dataLimite);
}