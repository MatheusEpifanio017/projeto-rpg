
package com.rpg.model;

public class PassivaRacial {

    public enum Tipo {
        ADAPTABILIDADE,
        FURIA_ANCESTRAL,
        REFLEXOS_ELFICOS,
        RESILIENCIA
    }

    private final String nome;
    private final String descricao;
    private final String condicao;
    private final Tipo tipo;
    private final int intensidade;

    public PassivaRacial(
            String nome,
            String descricao,
            String condicao,
            Tipo tipo,
            int intensidade) {

        this.nome = nome;
        this.descricao = descricao;
        this.condicao = condicao;
        this.tipo = tipo;
        this.intensidade = intensidade;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCondicao() { return condicao; }
    public Tipo getTipo() { return tipo; }
    public int getIntensidade() { return intensidade; }
}
