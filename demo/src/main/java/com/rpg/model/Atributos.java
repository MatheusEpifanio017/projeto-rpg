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

    public Atributos somar(Atributos bonus) {
        if (bonus == null) {
            throw new IllegalArgumentException(
                    "Os atributos bônus não podem ser nulos.");
        }

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
        return calcularModificador(forca);
    }

    public int getModificadorDestreza() {
        return calcularModificador(destreza);
    }

    public int getModificadorConstituicao() {
        return calcularModificador(constituicao);
    }

    public int getModificadorInteligencia() {
        return calcularModificador(inteligencia);
    }

    public int getModificadorSabedoria() {
        return calcularModificador(sabedoria);
    }

    public int getModificadorCarisma() {
        return calcularModificador(carisma);
    }

    private int calcularModificador(int atributo) {
        return Math.floorDiv(atributo - 10, 2);
    }
}