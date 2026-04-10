package org.example.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.example.dto.AgregacaoDTO;
import org.example.dto.PrecoMedioDTO;
import org.example.dto.ProdutoDTO;
import org.example.model.entities.Produto;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ElasticsearchClient client;

    public ProdutoService(ElasticsearchClient client) {
        this.client = client;
    }

    private List<ProdutoDTO> extrairResultados(SearchResponse<Produto> response) {
        return response.hits().hits().stream()
                .map(ProdutoDTO::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> buscarPorNome(String termo) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.match(m -> m.field("nome").query(termo))),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarPorDescricao(String termo) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.match(m -> m.field("descricao").query(termo))),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarPorFraseExata(String termo) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.matchPhrase(m -> m.field("descricao").query(termo))),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarFuzzy(String termo) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.match(m -> m
                                .field("nome")
                                .query(termo)
                                .fuzziness("AUTO")
                        )),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarEmMultiplosCampos(String termo) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.multiMatch(m -> m
                                .query(termo)
                                .fields("nome", "descricao")
                        )),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarComFiltroCategoria(String termo, String categoria) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.bool(b -> b
                                .must(m -> m.match(t -> t
                                        .field("descricao")
                                        .query(termo)))
                                .filter(f -> f.match(m -> m
                                        .field("categoria")
                                        .query(categoria)))
                        )),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarPorFaixaDePreco(Double min, Double max) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.range(r -> r
                                .number(n -> n
                                        .field("preco")
                                        .gte(min)
                                        .lte(max)
                                )
                        )),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<ProdutoDTO> buscarBuscaAvancada(String categoria, String raridade, Double min, Double max) throws IOException {
        SearchResponse<Produto> response = client.search(s -> s
                        .index("guilda_loja")
                        .query(q -> q.bool(b -> b
                                .filter(f -> f.match(m -> m.field("categoria").query(categoria)))
                                .filter(f -> f.match(m -> m.field("raridade").query(raridade)))
                                .filter(f -> f.range(r -> r
                                        .number(n -> n
                                                .field("preco")
                                                .gte(min)
                                                .lte(max)
                                        )
                                ))
                        )),
                Produto.class
        );
        return extrairResultados(response);
    }

    public List<AgregacaoDTO> agregarPorCategoria() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("por_categoria", a -> a.terms(t -> t.field("categoria"))),
                Void.class
        );

        return response.aggregations().get("por_categoria").sterms().buckets().array().stream()
                .map(b -> new AgregacaoDTO(b.key().stringValue(), b.docCount()))
                .collect(Collectors.toList());
    }

    public List<AgregacaoDTO> agregarPorRaridade() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("por_raridade", a -> a.terms(t -> t.field("raridade"))),
                Void.class
        );

        return response.aggregations().get("por_raridade").sterms().buckets().array().stream()
                .map(b -> new AgregacaoDTO(b.key().stringValue(), b.docCount()))
                .collect(Collectors.toList());
    }

    public PrecoMedioDTO obterPrecoMedio() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("preco_medio", a -> a.avg(avg -> avg.field("preco"))),
                Void.class
        );

        Double media = response.aggregations().get("preco_medio").avg().value();
        return new PrecoMedioDTO(media);
    }

    public List<AgregacaoDTO> obterFaixasDePreco() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("faixas", a -> a.range(r -> r
                                .field("preco")
                                .ranges(rg -> rg.to(100.0))
                                .ranges(rg -> rg.from(100.0).to(300.0))
                                .ranges(rg -> rg.from(300.0).to(700.0))
                                .ranges(rg -> rg.from(700.0))
                        )),
                Void.class
        );

        return response.aggregations().get("faixas").range().buckets().array().stream()
                .map(b -> {
                    String chaveFormatada = switch (b.key()) {
                        case "*-100.0" -> "Abaixo de 100";
                        case "100.0-300.0" -> "De 100 a 300";
                        case "300.0-700.0" -> "De 300 a 700";
                        case "700.0-*" -> "Acima de 700";
                        default -> b.key();
                    };
                    return new AgregacaoDTO(chaveFormatada, b.docCount());
                })
                .collect(Collectors.toList());
    }
}