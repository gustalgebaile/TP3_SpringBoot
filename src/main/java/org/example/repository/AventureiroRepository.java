package org.example.repository;

import org.example.enums.ClasseAventureiro;
import org.example.model.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @Query("SELECT a FROM Aventureiro a WHERE " +
            "(:classe IS NULL OR a.classe = :classe) AND " +
            "(:ativo IS NULL OR a.ativo = :ativo) AND " +
            "(:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)")
    Page<Aventureiro> buscarFiltrado(
            @Param("classe") ClasseAventureiro classe,
            @Param("ativo") Boolean ativo,
            @Param("nivelMinimo") Integer nivelMinimo,
            Pageable pageable);

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}