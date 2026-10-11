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

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "A classe precisa de um nome.");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "A classe precisa de uma descrição.");
        }

        if (atributosBase == null) {
            throw new IllegalArgumentException(
                    "Os atributos base são obrigatórios.");
        }

        if (pontosVidaBase <= 0) {
            throw new IllegalArgumentException(
                    "Os pontos de vida devem ser positivos.");
        }

        if (habilidadesIniciais == null) {
            throw new IllegalArgumentException(
                    "A lista de habilidades iniciais é obrigatória.");
        }

        if (habilidadesIniciais.stream().anyMatch(
                habilidade -> habilidade == null
                        || habilidade.isBlank())) {
            throw new IllegalArgumentException(
                    "As habilidades iniciais não podem ser vazias.");
        }

        this.nome = nome.trim();
        this.descricao = descricao.trim();
        this.atributosBase = atributosBase;
        this.pontosVidaBase = pontosVidaBase;
        this.habilidadesIniciais = List.copyOf(habilidadesIniciais);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Atributos getAtributosBase() {
        return atributosBase;
    }

    public int getPontosVidaBase() {
        return pontosVidaBase;
    }

    public List<String> getHabilidadesIniciais() {
        return habilidadesIniciais;
    }
}
