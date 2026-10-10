
package com.rpg.model;

public class Personagem {

    private final String nome;
    private final Classe classe;
    private final Raca raca;
    private final Atributos atributosBase;
    private final Atributos atributosFinais;
    private final int pontosVidaMaximos;
    private int pontosVidaAtuais;
    private int nivel;

    public Personagem(
            String nome,
            Classe classe,
            Raca raca,
            Atributos atributosBase,
            Atributos atributosFinais,
            int pontosVidaMaximos) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O personagem precisa de um nome.");
        }

        this.nome = nome;
        this.classe = classe;
        this.raca = raca;
        this.atributosBase = atributosBase;
        this.atributosFinais = atributosFinais;
        this.pontosVidaMaximos = pontosVidaMaximos;
        this.pontosVidaAtuais = pontosVidaMaximos;
        this.nivel = 1;
    }

    public String getNome() { return nome; }
    public Classe getClasse() { return classe; }
    public Raca getRaca() { return raca; }
    public Atributos getAtributosBase() { return atributosBase; }
    public Atributos getAtributosFinais() { return atributosFinais; }
    public int getPontosVidaMaximos() { return pontosVidaMaximos; }
    public int getPontosVidaAtuais() { return pontosVidaAtuais; }
    public int getNivel() { return nivel; }

    public PassivaRacial getPassivaRacial() {
        return raca.getPassiva();
    }

    public void receberDano(int dano) {
        if (dano < 0) {
            throw new IllegalArgumentException(
                    "O dano não pode ser negativo.");
        }

        pontosVidaAtuais = Math.max(0, pontosVidaAtuais - dano);
    }

    public void curar(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A cura não pode ser negativa.");
        }

        pontosVidaAtuais = Math.min(
                pontosVidaMaximos,
                pontosVidaAtuais + quantidade);
    }
}
