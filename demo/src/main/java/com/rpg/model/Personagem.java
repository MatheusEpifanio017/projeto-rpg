
package com.rpg.model;

public class Personagem {

    private String nome;
    private String raca;
    private int nivel = 1;

    private Atributos atributos;
    private Classe classe;
    private Subclasse subclasse;

    public Personagem(
            String nome,
            String raca,
            Atributos atributos) {

        this.nome = nome;
        this.raca = raca;
        this.atributos = atributos;
    }

    public void escolherClasse(Classe classe) {
        this.classe = classe;
        this.subclasse = null;
    }

    public void escolherSubclasse(Subclasse subclasse) {

        if (classe == null) {
            throw new IllegalStateException(
                "Escolha uma classe antes da subclasse."
            );
        }

        if (!classe.getNome().equalsIgnoreCase(
                subclasse.getClasseNome())) {
            throw new IllegalArgumentException(
                "Essa subclasse não pertence à classe escolhida."
            );
        }

        this.subclasse = subclasse;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public int getNivel() {
        return nivel;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public Classe getClasse() {
        return classe;
    }

    public Subclasse getSubclasse() {
        return subclasse;
    }
}
