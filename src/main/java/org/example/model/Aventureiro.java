package org.example.model;

import org.example.enums.ClasseAventureiro;

public class Aventureiro {
    private Long id;
    private String nome;
    private ClasseAventureiro classe;
    private Integer nivel;
    private Boolean ativo;
    private Companheiro companheiro;

    public Aventureiro() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public ClasseAventureiro getClasse() {
        return classe;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Companheiro getCompanheiro() {
        return companheiro;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setClasse(ClasseAventureiro classe) {
        this.classe = classe;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setCompanheiro(Companheiro companheiro) {
        this.companheiro = companheiro;
    }
}
