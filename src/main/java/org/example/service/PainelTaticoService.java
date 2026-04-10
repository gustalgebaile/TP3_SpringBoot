package org.example.service;

import org.example.model.entities.PainelTatico;
import org.example.repository.PainelTaticoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PainelTaticoService {

    private final PainelTaticoRepository repository;

    public PainelTaticoService(PainelTaticoRepository repository) {
        this.repository = repository;
    }
    /* A anotação abaixo salva o retorno deste método em memória.
       Nas próximas chamadas, o Spring não executa o método, apenas devolve o valor do cache. */
    @Cacheable("topMissoesTaticas")
    public List<PainelTatico> obterTopMissoesRecentes() {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(15);

        return repository.findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(dataLimite);
    }
}
/*
Explicação da Questão 2 - Melhoria de Desempenho

Como eu resolvi: Caching direto na camada da aplicação usando Spring Cache.

O enunciado deixou bem claro que a gente não podia mexer na estrutura da Materialized View. Então, como eu não podia otimizar o banco,
a melhor saída foi puxar essa responsabilidade para o código da nossa aplicação,
usando cache em memória.

O que eu fiz no código:
1. Fui na classe principal do projeto (`Tp3Application.java`) e coloquei a anotação `@EnableCaching` para "ligar" o suporte a cache do Spring Boot.
2. Depois, fui no `PainelTaticoService` e coloquei um `@Cacheable("topMissoesTaticas")` bem em cima do método `obterTopMissoesRecentes()`.

O resultado: Agora, a primeira requisição bate no banco para realizar a consulta pesada com os JOINs e agregações. Porém as requisições seguintes não chegam mais no banco;
o Spring intercepta e devolve a lista que ficou salva na memória RAM da aplicação. Resolveu a lentidão e poupou o banco de dados!
*/