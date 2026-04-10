package org.example.dto;

import co.elastic.clients.elasticsearch.core.search.Hit;
import org.example.model.entities.Produto;

public record ProdutoDTO(
        String id,
        String nome,
        String descricao,
        String categoria,
        String raridade,
        Double preco,
        Double score
) {
    public static ProdutoDTO toDTO(Hit<Produto> hit) {
        Produto produto = hit.source();
        return new ProdutoDTO(
                hit.id(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getRaridade(),
                produto.getPreco(),
                hit.score()
        );
    }

    public static ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getRaridade(),
                produto.getPreco(),
                null
        );
    }
}