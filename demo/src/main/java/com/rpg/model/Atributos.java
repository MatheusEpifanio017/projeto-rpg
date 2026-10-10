
package com.rpg.model;

public class Atributos {

    private final int forca;
    private final int destreza;
    private final int constituicao;
    private final int inteligencia;
    private final int sabedoria;
    private final int carisma;

    public Atributos(
            int forca,
            int destreza,
            int constituicao,
            int inteligencia,
            int sabedoria,
            int carisma) {

        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.inteligencia = inteligencia;
        this.sabedoria = sabedoria;
        this.carisma = carisma;
    }

    public int getForca() { return forca; }
    public int getDestreza() { return destreza; }
    public int getConstituicao() { return constituicao; }
    public int getInteligencia() { return inteligencia; }
    public int getSabedoria() { return sabedoria; }
    public int getCarisma() { return carisma; }

    public Atributos somar(Atributos bonus) {
        return new Atributos(
                forca + bonus.forca,
                destreza + bonus.destreza,
                constituicao + bonus.constituicao,
                inteligencia + bonus.inteligencia,
                sabedoria + bonus.sabedoria,
                carisma + bonus.carisma
        );
    }

    public int getModificadorForca() {
        return (forca - 10) / 2;
    }

    public int getModificadorDestreza() {
        return (destreza - 10) / 2;
    }

    public int getModificadorConstituicao() {
        return (constituicao - 10) / 2;
    }

    public int getModificadorInteligencia() {
        return (inteligencia - 10) / 2;
    }

    public int getModificadorSabedoria() {
        return (sabedoria - 10) / 2;
    }

    public int getModificadorCarisma() {
        return (carisma - 10) / 2;
    }
}
