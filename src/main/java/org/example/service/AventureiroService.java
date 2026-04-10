package org.example.service;

import org.example.dto.*;
import org.example.enums.ClasseAventureiro;
import org.example.exception.RecursoNaoEncontradoException;
import org.example.model.entities.Aventureiro;
import org.example.model.entities.Companheiro;
import org.example.model.entities.ParticipacaoMissao;
import org.example.model.audit.Organizacao;
import org.example.model.audit.Usuario;
import org.example.repository.AventureiroRepository;
import org.example.repository.OrganizacaoRepository;
import org.example.repository.ParticipacaoMissaoRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;

    public AventureiroService(AventureiroRepository aventureiroRepository,
                              OrganizacaoRepository organizacaoRepository,
                              UsuarioRepository usuarioRepository,
                              ParticipacaoMissaoRepository participacaoRepository) {
        this.aventureiroRepository = aventureiroRepository;
        this.organizacaoRepository = organizacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.participacaoRepository = participacaoRepository;
    }

    @Transactional
    public AventureiroDetalheDTO registrar(AventureiroRequestDTO dto) {
        Organizacao org = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organização não encontrada."));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        Aventureiro a = new Aventureiro();
        a.setNome(dto.nome());
        a.setClasse(dto.classe());
        a.setNivel(dto.nivel());
        a.setAtivo(true);
        a.setOrganizacao(org);
        a.setUsuarioResponsavel(usuario);

        Aventureiro salvo = aventureiroRepository.save(a);
        return toDetalheDTO(salvo);
    }

    public Aventureiro buscarPorId(Long id) {
        return aventureiroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado."));
    }

    public AventureiroDetalheDTO buscarDetalhesPorId(Long id) {
        return toDetalheDTO(buscarPorId(id));
    }

    @Transactional
    public AventureiroDetalheDTO atualizar(Long id, AventureiroRequestDTO dto) {
        Aventureiro a = buscarPorId(id);
        a.setNome(dto.nome());
        a.setClasse(dto.classe());
        a.setNivel(dto.nivel());
        return toDetalheDTO(aventureiroRepository.save(a));
    }

    @Transactional
    public void alterarStatus(Long id, boolean ativo) {
        Aventureiro a = buscarPorId(id);
        a.setAtivo(ativo);
        aventureiroRepository.save(a);
    }

    @Transactional
    public AventureiroDetalheDTO definirCompanheiro(Long id, CompanheiroDTO dto) {
        Aventureiro a = buscarPorId(id);

        Companheiro c = a.getCompanheiro();
        if (c == null) {
            c = new Companheiro();
            c.setAventureiro(a);
            a.setCompanheiro(c);
        }
        c.setNome(dto.nome());
        c.setEspecie(dto.especie());
        c.setLealdade(dto.lealdade());

        return toDetalheDTO(aventureiroRepository.save(a));
    }

    @Transactional
    public void removerCompanheiro(Long id) {
        Aventureiro a = buscarPorId(id);
        a.setCompanheiro(null);
        aventureiroRepository.save(a);
    }

    public PageResult<AventureiroResumoDTO> listarPaginado(ClasseAventureiro classe, Boolean ativo, Integer nivelMinimo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Aventureiro> pagina = aventureiroRepository.buscarFiltrado(classe, ativo, nivelMinimo, pageable);

        List<AventureiroResumoDTO> itens = pagina.getContent().stream()
                .map(a -> new AventureiroResumoDTO(a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()))
                .collect(Collectors.toList());

        return new PageResult<>(itens, (int) pagina.getTotalElements(), pagina.getNumber(), pagina.getSize(), pagina.getTotalPages());
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
    public PageResult<AventureiroResumoDTO> buscarPorNome(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<Aventureiro> pagina = aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable);

        List<AventureiroResumoDTO> itens = pagina.getContent().stream()
                .map(a -> new AventureiroResumoDTO(a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()))
                .collect(Collectors.toList());

        return new PageResult<>(itens, (int) pagina.getTotalElements(), pagina.getNumber(), pagina.getSize(), pagina.getTotalPages());
    }

    public AventureiroPerfilDTO buscarPerfilCompleto(Long id) {
        Aventureiro a = buscarPorId(id);

        Long totalParticipacoes = participacaoRepository.contarParticipacoesPorAventureiro(id);

        Optional<ParticipacaoMissao> ultimaPart = participacaoRepository.buscarUltimaParticipacaoPorAventureiro(id);
        String tituloUltimaMissao = ultimaPart.map(p -> p.getMissao().getTitulo()).orElse(null);

        CompanheiroDTO compDTO = null;
        if (a.getCompanheiro() != null) {
            compDTO = new CompanheiroDTO(
                    a.getCompanheiro().getNome(),
                    a.getCompanheiro().getEspecie(),
                    a.getCompanheiro().getLealdade()
            );
        }

        return new AventureiroPerfilDTO(
                a.getId(),
                a.getNome(),
                a.getClasse(),
                a.getNivel(),
                a.getAtivo(),
                compDTO,
                totalParticipacoes,
                tituloUltimaMissao
        );
    }
}