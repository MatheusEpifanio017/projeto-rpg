
package com.rpg.model;

import java.util.List;

public class Subclasse {

    private String nome;
    private String classeNome;
    private String descricao;
    private List<String> habilidades;

    public Subclasse(
            String nome,
            String classeNome,
            String descricao,
            List<String> habilidades) {

        this.nome = nome;
        this.classeNome = classeNome;
        this.descricao = descricao;
        this.habilidades = List.copyOf(habilidades);
    }

    public String getNome() {
        return nome;
    }

    public String getClasseNome() {
        return classeNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }
}
