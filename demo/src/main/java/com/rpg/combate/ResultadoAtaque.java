
package com.rpg.combate;

public class ResultadoAtaque {

    private final boolean acertou;
    private final int rolagem;
    private final int valorAtaque;
    private final int defesaAlvo;
    private final int dano;
    private final TipoDano tipoDano;
    private final boolean alvoDerrotado;

    public ResultadoAtaque(
            boolean acertou,
            int rolagem,
            int valorAtaque,
            int defesaAlvo,
            int dano,
            TipoDano tipoDano,
            boolean alvoDerrotado) {

        this.acertou = acertou;
        this.rolagem = rolagem;
        this.valorAtaque = valorAtaque;
        this.defesaAlvo = defesaAlvo;
        this.dano = dano;
        this.tipoDano = tipoDano;
        this.alvoDerrotado = alvoDerrotado;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public int getRolagem() {
        return rolagem;
    }

    public int getValorAtaque() {
        return valorAtaque;
    }

    public int getDefesaAlvo() {
        return defesaAlvo;
    }

    public int getDano() {
        return dano;
    }

    public TipoDano getTipoDano() {
        return tipoDano;
    }

    public boolean isAlvoDerrotado() {
        return alvoDerrotado;
    }
}
