package org.example;

import org.example.model.audit.Organizacao;
import org.example.model.audit.Role;
import org.example.model.audit.Usuario;
import org.example.repository.OrganizacaoRepository;
import org.example.repository.RoleRepository;
import org.example.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// O replace = NONE força o teste a rodar contra o banco PostgreSQL no Docker, e não no H2 em memória.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SistemaLegadoMappingTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Rollback // Desfaz as alterações após o teste para manter o banco limpo
    void devePersistirUsuarioERecuperarComSuasRolesEOrganizacao() {
        // 1. Busca ou Cria uma Organização para o teste
        Organizacao org = new Organizacao();
        org.setNome("Guilda de Teste " + System.currentTimeMillis());
        org.setAtivo(true);
        org.setCreatedAt(OffsetDateTime.now());
        org = organizacaoRepository.save(org);

        // 2. Busca ou Cria uma Role
        Role roleAdmin = new Role();
        roleAdmin.setNome("ADMIN_" + System.currentTimeMillis());
        roleAdmin.setOrganizacao(org);
        roleAdmin.setCreatedAt(OffsetDateTime.now());
        roleAdmin = roleRepository.save(roleAdmin);

        // 3. Cria e persiste um novo Usuário
        Usuario usuario = new Usuario();
        usuario.setNome("Arthas Menethil");
        usuario.setEmail("arthas" + System.currentTimeMillis() + "@lordaeron.com");
        usuario.setSenhaHash("hash_super_seguro");
        usuario.setStatus("ATIVO");
        usuario.setCreatedAt(OffsetDateTime.now());
        usuario.setUpdatedAt(OffsetDateTime.now());
        usuario.setOrganizacao(org);
        usuario.getRoles().add(roleAdmin); // Vincula a Role

        Usuario salvo = usuarioRepository.save(usuario);

        // Limpa o contexto do JPA para forçar uma consulta real ao banco
        usuarioRepository.flush();

        // 4. Valida as Asserções
        Optional<Usuario> encontrado = usuarioRepository.findById(salvo.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getOrganizacao().getNome()).isEqualTo(org.getNome());
        assertThat(encontrado.get().getRoles()).hasSize(1);
        assertThat(encontrado.get().getRoles().iterator().next().getNome()).isEqualTo(roleAdmin.getNome());
    }
}