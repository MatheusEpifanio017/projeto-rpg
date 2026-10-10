
package com.rpg.model;

public class Atributos {

    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

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

    public int getForca() {
        return forca;
    }

    public int getDestreza() {
        return destreza;
    }

    public int getConstituicao() {
        return constituicao;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public int getSabedoria() {
        return sabedoria;
    }

    public int getCarisma() {
        return carisma;
    }

    public int calcularModificador(int valor) {
        return (int) Math.floor((valor - 10) / 2.0);
    }

    public int getModificadorForca() {
        return calcularModificador(forca);
    }

    public int getModificadorDestreza() {
        return calcularModificador(destreza);
    }
}
