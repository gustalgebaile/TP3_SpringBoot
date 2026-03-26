package org.example;

import org.example.dto.AventureiroDetalheDTO;
import org.example.dto.AventureiroRequestDTO;
import org.example.enums.ClasseAventureiro;
import org.example.model.audit.Organizacao;
import org.example.model.audit.Usuario;
import org.example.repository.OrganizacaoRepository;
import org.example.repository.UsuarioRepository;
import org.example.service.AventureiroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AventuraIntegrationTest {

    @Autowired
    private AventureiroService aventureiroService;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveRegistrarAventureiroCruzandoSchemasComSucesso() {
        Organizacao org = new Organizacao();
        org.setNome("Guilda do Lótus Branco " + System.currentTimeMillis());
        org.setAtivo(true);
        org.setCreatedAt(OffsetDateTime.now());
        org = organizacaoRepository.save(org);

        Usuario user = new Usuario();
        user.setNome("Mestre Pai Mei");
        user.setEmail("paimei" + System.currentTimeMillis() + "@lotus.com");
        user.setSenhaHash("hash_seguro_123");
        user.setStatus("ATIVO");
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user.setOrganizacao(org);
        user = usuarioRepository.save(user);

        AventureiroRequestDTO request = new AventureiroRequestDTO(
                "Geralt de Rívia",
                ClasseAventureiro.GUERREIRO,
                35,
                org.getId(),
                user.getId()
        );

        AventureiroDetalheDTO response = aventureiroService.registrar(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.nome()).isEqualTo("Geralt de Rívia");
        assertThat(response.classe()).isEqualTo(ClasseAventureiro.GUERREIRO);
        assertThat(response.ativo()).isTrue();

        System.out.println("SUCESSO! Aventureiro ID " + response.id() + " salvo no schema aventura!");
    }
}