
package com.rpg.model;

public class Classe {

    private String nome;
    private String descricao;
    private String atributoPrincipal;
    private int dadoDeVida;

    public Classe(
            String nome,
            String descricao,
            String atributoPrincipal,
            int dadoDeVida) {

        this.nome = nome;
        this.descricao = descricao;
        this.atributoPrincipal = atributoPrincipal;
        this.dadoDeVida = dadoDeVida;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAtributoPrincipal() {
        return atributoPrincipal;
    }

    public int getDadoDeVida() {
        return dadoDeVida;
    }
}
