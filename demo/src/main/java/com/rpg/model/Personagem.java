
package com.rpg.model;

public class Personagem {

    private final String nome;
    private final Classe classe;
    private final Raca raca;
    private final Atributos atributosBase;
    private final Atributos atributosFinais;
    private final int pontosVidaMaximos;
    private final int defesaBase;
    private final int energiaMaxima;

    private int pontosVidaAtuais;
    private int energiaAtual;
    private int nivel;
    private boolean defendendo;

    public Personagem(
            String nome,
            Classe classe,
            Raca raca,
            Atributos atributosBase,
            Atributos atributosFinais,
            int pontosVidaMaximos) {

        this(
                nome,
                classe,
                raca,
                atributosBase,
                atributosFinais,
                pontosVidaMaximos,
                calcularDefesa(atributosFinais),
                calcularEnergia(classe)
        );
    }

    public Personagem(
            String nome,
            Classe classe,
            Raca raca,
            Atributos atributosBase,
            Atributos atributosFinais,
            int pontosVidaMaximos,
            int defesaBase,
            int energiaMaxima) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O personagem precisa de um nome.");
        }

        if (classe == null || raca == null
                || atributosBase == null
                || atributosFinais == null) {
            throw new IllegalArgumentException(
                    "Classe, raça e atributos são obrigatórios.");
        }

        if (pontosVidaMaximos <= 0) {
            throw new IllegalArgumentException(
                    "Os pontos de vida máximos devem ser positivos.");
        }

        if (defesaBase < 0) {
            throw new IllegalArgumentException(
                    "A defesa não pode ser negativa.");
        }

        if (energiaMaxima < 0) {
            throw new IllegalArgumentException(
                    "A energia máxima não pode ser negativa.");
        }

        this.nome = nome;
        this.classe = classe;
        this.raca = raca;
        this.atributosBase = atributosBase;
        this.atributosFinais = atributosFinais;
        this.pontosVidaMaximos = pontosVidaMaximos;
        this.defesaBase = defesaBase;
        this.energiaMaxima = energiaMaxima;

        this.pontosVidaAtuais = pontosVidaMaximos;
        this.energiaAtual = energiaMaxima;
        this.nivel = 1;
        this.defendendo = false;
    }

    private static int calcularDefesa(Atributos atributos) {
        if (atributos == null) {
            throw new IllegalArgumentException(
                    "Os atributos são obrigatórios.");
        }

        return 10 + atributos.getModificadorDestreza();
    }

    private static int calcularEnergia(Classe classe) {
        if (classe == null) {
            throw new IllegalArgumentException(
                    "A classe é obrigatória.");
        }

        return switch (classe.getNome()) {
            case "Feiticeiro" -> 6;
            case "Clérigo" -> 4;
            default -> 3;
        };
    }

    public String getNome() {
        return nome;
    }

    public Classe getClasse() {
        return classe;
    }

    public Raca getRaca() {
        return raca;
    }

    public Atributos getAtributosBase() {
        return atributosBase;
    }

    public Atributos getAtributosFinais() {
        return atributosFinais;
    }

    public int getPontosVidaMaximos() {
        return pontosVidaMaximos;
    }

    public int getPontosVidaAtuais() {
        return pontosVidaAtuais;
    }

    public int getDefesaBase() {
        return defesaBase;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public int getEnergiaAtual() {
        return energiaAtual;
    }

    public int getNivel() {
        return nivel;
    }

    public boolean isDefendendo() {
        return defendendo;
    }

    public boolean isDerrotado() {
        return pontosVidaAtuais <= 0;
    }

    public PassivaRacial getPassivaRacial() {
        return raca.getPassiva();
    }

    public int getDefesaAtual() {
        return defesaBase + (defendendo ? 3 : 0);
    }

    public void receberDano(int dano) {
        if (dano < 0) {
            throw new IllegalArgumentException(
                    "O dano não pode ser negativo.");
        }

        pontosVidaAtuais = Math.max(
                0, pontosVidaAtuais - dano);
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

    public boolean consumirEnergia(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de energia não pode ser negativa.");
        }

        if (energiaAtual < quantidade) {
            return false;
        }

        energiaAtual -= quantidade;
        return true;
    }

    public void recuperarEnergia(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de energia não pode ser negativa.");
        }

        energiaAtual = Math.min(
                energiaMaxima,
                energiaAtual + quantidade);
    }

    public void defender() {
        defendendo = true;
    }

    public void encerrarDefesa() {
        defendendo = false;
    }

    public void subirNivel() {
        nivel++;
    }

    public void subirNiveis(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalArgumentException(
                    "A quantidade de níveis deve ser positiva.");
        }

        nivel += quantidade;
    }

    public void restaurarProgresso(
            int nivel, int pontosVidaAtuais) {

        restaurarProgresso(
                nivel, pontosVidaAtuais, energiaAtual);
    }

    public void restaurarProgresso(
            int nivel,
            int pontosVidaAtuais,
            int energiaAtual) {

        if (nivel < 1) {
            throw new IllegalArgumentException(
                    "O nível deve ser pelo menos 1.");
        }

        if (pontosVidaAtuais < 0
                || pontosVidaAtuais > pontosVidaMaximos) {
            throw new IllegalArgumentException(
                    "Os pontos de vida informados são inválidos.");
        }

        if (energiaAtual < 0
                || energiaAtual > energiaMaxima) {
            throw new IllegalArgumentException(
                    "A energia informada é inválida.");
        }

        this.nivel = nivel;
        this.pontosVidaAtuais = pontosVidaAtuais;
        this.energiaAtual = energiaAtual;
        this.defendendo = false;
    }
}
