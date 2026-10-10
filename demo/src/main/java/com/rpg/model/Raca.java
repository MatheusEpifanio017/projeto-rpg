
package com.rpg.model;

public class Raca {

    private final String nome;
    private final Atributos bonusAtributos;
    private final PassivaRacial passiva;

    public Raca(
            String nome,
            Atributos bonusAtributos,
            PassivaRacial passiva) {

        this.nome = nome;
        this.bonusAtributos = bonusAtributos;
        this.passiva = passiva;
    }

    public String getNome() { return nome; }

    public Atributos getBonusAtributos() {
        return bonusAtributos;
    }

    public PassivaRacial getPassiva() {
        return passiva;
    }
}
