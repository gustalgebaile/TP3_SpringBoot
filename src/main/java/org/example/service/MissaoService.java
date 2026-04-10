package org.example.service;

import org.example.dto.*;
import org.example.enums.NivelPerigo;
import org.example.enums.StatusMissao;
import org.example.exception.RecursoNaoEncontradoException;
import org.example.model.entities.Missao;
import org.example.model.entities.ParticipacaoMissao;
import org.example.repository.MissaoRepository;
import org.example.repository.ParticipacaoMissaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;

    public MissaoService(MissaoRepository missaoRepository, ParticipacaoMissaoRepository participacaoRepository) {
        this.missaoRepository = missaoRepository;
        this.participacaoRepository = participacaoRepository;
    }

    public PageResult<MissaoResumoDTO> listarPaginado(StatusMissao status, NivelPerigo nivel, OffsetDateTime dataInicio, OffsetDateTime dataFim, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Missao> pagina = missaoRepository.buscarFiltrado(status, nivel, dataInicio, dataFim, pageable);

        List<MissaoResumoDTO> itens = pagina.getContent().stream()
                .map(m -> new MissaoResumoDTO(m.getId(), m.getTitulo(), m.getStatus(), m.getNivelPerigo(), m.getDataInicio(), m.getDataTermino()))
                .collect(Collectors.toList());

        return new PageResult<>(itens, (int) pagina.getTotalElements(), pagina.getNumber(), pagina.getSize(), pagina.getTotalPages());
    }

    public MissaoDetalheDTO buscarDetalhes(Long missaoId) {
        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada."));

        List<ParticipacaoMissao> participacoes = participacaoRepository.findByMissaoId(missaoId);

        List<ParticipanteMissaoDTO> participantesDTO = participacoes.stream()
                .map(p -> new ParticipanteMissaoDTO(
                        p.getAventureiro().getId(),
                        p.getAventureiro().getNome(),
                        p.getPapelNaMissao(),
                        p.getRecompensaOuro(),
                        p.getDestaqueMvp()
                ))
                .collect(Collectors.toList());

        return new MissaoDetalheDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelPerigo(),
                missao.getDataInicio(),
                missao.getDataTermino(),
                participantesDTO
        );
    }
}