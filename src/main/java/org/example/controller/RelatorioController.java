package org.example.controller;

import org.example.dto.RankingAventureiroDTO;
import org.example.dto.RelatorioMissaoDTO;
import org.example.enums.StatusMissao;
import org.example.repository.MissaoRepository;
import org.example.repository.ParticipacaoMissaoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final ParticipacaoMissaoRepository participacaoRepository;
    private final MissaoRepository missaoRepository;

    public RelatorioController(ParticipacaoMissaoRepository participacaoRepository, MissaoRepository missaoRepository) {
        this.participacaoRepository = participacaoRepository;
        this.missaoRepository = missaoRepository;
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingAventureiroDTO>> obterRanking(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @RequestParam(required = false) StatusMissao status) {
        return ResponseEntity.ok(participacaoRepository.gerarRankingAventureiros(dataInicio, dataFim, status));
    }

    @GetMapping("/missoes-metricas")
    public ResponseEntity<List<RelatorioMissaoDTO>> obterRelatorioMissoes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim) {
        return ResponseEntity.ok(missaoRepository.gerarRelatorioMissoes(dataInicio, dataFim));
    }
}