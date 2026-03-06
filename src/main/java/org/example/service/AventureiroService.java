package org.example.service;

import org.example.dto.*;
import org.example.enums.ClasseAventureiro;
import org.example.model.Aventureiro;
import org.example.model.Companheiro;
import org.example.repository.AventureiroRepository;
import org.example.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AventureiroService {

    private final AventureiroRepository repository;

    public AventureiroService(AventureiroRepository repository) {
        this.repository = repository;
    }

    public AventureiroDetalheDTO registrar(AventureiroRequestDTO dto) {
        Aventureiro a = new Aventureiro();
        a.setNome(dto.nome());
        a.setClasse(dto.classe());
        a.setNivel(dto.nivel());
        a.setAtivo(true);

        Aventureiro salvo = repository.save(a);
        return toDetalheDTO(salvo);
    }

    public Aventureiro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado."));
    }

    public AventureiroDetalheDTO buscarDetalhesPorId(Long id) {
        return toDetalheDTO(buscarPorId(id));
    }

    public AventureiroDetalheDTO atualizar(Long id, AventureiroRequestDTO dto) {
        Aventureiro a = buscarPorId(id);
        a.setNome(dto.nome());
        a.setClasse(dto.classe());
        a.setNivel(dto.nivel());
        return toDetalheDTO(repository.save(a));
    }

    public void alterarStatus(Long id, boolean ativo) {
        Aventureiro a = buscarPorId(id);
        a.setAtivo(ativo);
        repository.save(a);
    }

    public AventureiroDetalheDTO definirCompanheiro(Long id, CompanheiroDTO dto) {
        Aventureiro a = buscarPorId(id);
        Companheiro c = new Companheiro();
        c.setNome(dto.nome());
        c.setEspecie(dto.especie());
        c.setLealdade(dto.lealdade());
        a.setCompanheiro(c);
        return toDetalheDTO(repository.save(a));
    }

    public void removerCompanheiro(Long id) {
        Aventureiro a = buscarPorId(id);
        a.setCompanheiro(null);
        repository.save(a);
    }

    public PageResult<AventureiroResumoDTO> listarPaginado(ClasseAventureiro classe, Boolean ativo, Integer nivelMinimo, int page, int size) {
        List<Aventureiro> filtrados = repository.findAllFiltered(classe, ativo, nivelMinimo);

        int total = filtrados.size();
        int totalPages = (int) Math.ceil((double) total / size);
        int start = Math.min(page * size, total);
        int end = Math.min((page + 1) * size, total);

        List<AventureiroResumoDTO> itens = filtrados.subList(start, end).stream()
                .map(a -> new AventureiroResumoDTO(a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()))
                .collect(Collectors.toList());

        return new PageResult<>(itens, total, page, size, totalPages);
    }
    private AventureiroDetalheDTO toDetalheDTO(Aventureiro a) {
        CompanheiroDTO compDTO = null;
        if (a.getCompanheiro() != null) {
            compDTO = new CompanheiroDTO(
                    a.getCompanheiro().getNome(),
                    a.getCompanheiro().getEspecie(),
                    a.getCompanheiro().getLealdade()
            );
        }
        return new AventureiroDetalheDTO(a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo(), compDTO);
    }
}