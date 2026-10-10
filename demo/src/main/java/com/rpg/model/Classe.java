
package com.rpg.model;

import java.util.List;

public class Classe {

    private final String nome;
    private final String descricao;
    private final Atributos atributosBase;
    private final int pontosVidaBase;
    private final List<String> habilidadesIniciais;

    public Classe(
            String nome,
            String descricao,
            Atributos atributosBase,
            int pontosVidaBase,
            List<String> habilidadesIniciais) {

        if (pontosVidaBase <= 0) {
            throw new IllegalArgumentException(
                    "Os pontos de vida devem ser positivos.");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.atributosBase = atributosBase;
        this.pontosVidaBase = pontosVidaBase;
        this.habilidadesIniciais =
                List.copyOf(habilidadesIniciais);
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Atributos getAtributosBase() { return atributosBase; }
    public int getPontosVidaBase() { return pontosVidaBase; }

    public List<String> getHabilidadesIniciais() {
        return habilidadesIniciais;
    }
}
