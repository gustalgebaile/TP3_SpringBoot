package org.example.model;

import org.example.enums.EspecieCompanheiro;

public class Companheiro {
    private String nome;
    private EspecieCompanheiro especie;
    private Integer lealdade;

    public Companheiro() {
    }

    public Companheiro(String invencível, EspecieCompanheiro especieCompanheiro, int i) {
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public EspecieCompanheiro getEspecie() {
        return especie;
    }

    public Integer getLealdade() {
        return lealdade;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(EspecieCompanheiro especie) {
        this.especie = especie;
    }

    public void setLealdade(Integer lealdade) {
        this.lealdade = lealdade;
    }
}


