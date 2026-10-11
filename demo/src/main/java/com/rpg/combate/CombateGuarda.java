
package com.rpg.combate;

public class CombateGuarda {

    private int vidaJogador = 100;
    private int vidaGuarda = 60;
    private int energia = 5;
    private boolean defendendo = false;

    public String getEstado() {
        return "Combate iniciado!\n"
                + "Sua vida: " + vidaJogador
                + "\nVida do guarda: " + vidaGuarda
                + "\nEnergia: " + energia
                + "\nAções: ataque, defender ou habilidade";
    }

    public String executarTurno(String acao) {
        if (isVencido()) {
            return "Você já venceu o guarda.";
        }

        if (isDerrotado()) {
            return "Você foi derrotado pelo guarda.";
        }

        StringBuilder resultado = new StringBuilder();

        switch (acao.toLowerCase().trim()) {
            case "ataque" -> {
                vidaGuarda -= 15;
                resultado.append("Você atacou e causou 15 de dano.");
            }
            case "defender" -> {
                defendendo = true;
                energia = Math.min(5, energia + 1);
                resultado.append(
                        "Você assumiu uma postura defensiva e recuperou energia.");
            }
            case "habilidade" -> {
                if (energia >= 2) {
                    energia -= 2;
                    vidaGuarda -= 25;
                    resultado.append(
                            "Você usou uma habilidade e causou 25 de dano.");
                } else {
                    resultado.append("Energia insuficiente.");
                }
            }
            default -> {
                return "Ação inválida. Use ataque, defender ou habilidade.";
            }
        }

        if (vidaGuarda <= 0) {
            vidaGuarda = 0;
            return resultado
                    + "\nVocê derrotou o guarda!"
                    + "\nSua vida: " + vidaJogador
                    + "\nVida do guarda: 0"
                    + "\nEnergia: " + energia;
        }

        int danoGuarda = 10;
        if (defendendo) {
            danoGuarda = 3;
            defendendo = false;
        }

        vidaJogador -= danoGuarda;
        vidaJogador = Math.max(0, vidaJogador);

        resultado.append("\nO guarda atacou e causou ")
                .append(danoGuarda)
                .append(" de dano.")
                .append("\nSua vida: ").append(vidaJogador)
                .append("\nVida do guarda: ").append(vidaGuarda)
                .append("\nEnergia: ").append(energia);

        if (isDerrotado()) {
            resultado.append("\nVocê foi derrotado pelo guarda.");
        }

        return resultado.toString();
    }

    public boolean isVencido() {
        return vidaGuarda <= 0;
    }

    public boolean isDerrotado() {
        return vidaJogador <= 0;
    }
}
