package org.example.model.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder@ToString
@Document(indexName = "guilda_loja")
public class Produto {

    @Id
    private String id;

    private String nome;
    private String descricao;

    @Field(type = FieldType.Keyword)
    private String categoria;

    @Field(type = FieldType.Keyword)
    private String raridade;

    @Field(type = FieldType.Double)
    private Double preco;
}