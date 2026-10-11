package com.rpg.service;

import java.util.concurrent.ThreadLocalRandom;

import com.rpg.combate.ResultadoAtaque;
import com.rpg.combate.TipoDano;
import com.rpg.model.Atributos;
import com.rpg.model.PassivaRacial;
import com.rpg.model.Personagem;

public class CombateService {

    private int rolarD20() {
        return ThreadLocalRandom.current().nextInt(1, 21);
    }

    public int rolarIniciativa(Personagem personagem) {
        if (personagem == null) {
            throw new IllegalArgumentException(
                    "O personagem é obrigatório para rolar iniciativa.");
        }

        if (personagem.getPontosVidaAtuais() <= 0) {
            throw new IllegalStateException(
                    "Um personagem derrotado não pode participar do combate.");
        }

        int iniciativa = rolarD20()
                + personagem.getAtributosFinais()
                        .getModificadorDestreza();

        PassivaRacial passiva = personagem.getPassivaRacial();

        if (passiva != null
                && passiva.getTipo()
                        == PassivaRacial.Tipo.REFLEXOS_ELFICOS) {
            iniciativa += passiva.getIntensidade();
        }

        return iniciativa;
    }

    public ResultadoAtaque atacar(
            Personagem atacante,
            Personagem alvo,
            TipoDano tipoDano) {

        if (atacante == null || alvo == null) {
            throw new IllegalArgumentException(
                    "O atacante e o alvo são obrigatórios.");
        }

        if (tipoDano == null) {
            throw new IllegalArgumentException(
                    "O tipo de dano é obrigatório.");
        }

        if (atacante == alvo) {
            throw new IllegalArgumentException(
                    "Um personagem não pode atacar a si mesmo.");
        }

        if (atacante.getPontosVidaAtuais() <= 0) {
            throw new IllegalStateException(
                    "Um personagem derrotado não pode atacar.");
        }

        if (alvo.getPontosVidaAtuais() <= 0) {
            throw new IllegalStateException(
                    "Esse alvo já está derrotado.");
        }

        Atributos atributosAtacante =
                atacante.getAtributosFinais();

        Atributos atributosAlvo =
                alvo.getAtributosFinais();

        int modificadorOfensivo;

        if (tipoDano == TipoDano.FISICO) {
            modificadorOfensivo =
                    atributosAtacante.getModificadorForca();
        } else {
            modificadorOfensivo =
                    atributosAtacante.getModificadorInteligencia();
        }

        int rolagem = rolarD20();
        int valorAtaque = rolagem + modificadorOfensivo;

        int defesaAlvo = 10
                + atributosAlvo.getModificadorDestreza();

        boolean acertou = valorAtaque >= defesaAlvo;
        int dano = 0;

        if (acertou) {
            int reducao;

            if (tipoDano == TipoDano.FISICO) {
                reducao = Math.max(
                        0,
                        atributosAlvo
                                .getModificadorConstituicao());
            } else {
                reducao = Math.max(
                        0,
                        atributosAlvo
                                .getModificadorSabedoria());
            }

            dano = Math.max(
                    0,
                    5 + modificadorOfensivo - reducao);

            PassivaRacial passivaAlvo =
                    alvo.getPassivaRacial();

            if (passivaAlvo != null
                    && passivaAlvo.getTipo()
                            == PassivaRacial.Tipo.RESILIENCIA) {
                dano = Math.max(
                        0,
                        dano - passivaAlvo.getIntensidade());
            }

            PassivaRacial passivaAtacante =
                    atacante.getPassivaRacial();

            if (tipoDano == TipoDano.FISICO
                    && passivaAtacante != null
                    && passivaAtacante.getTipo()
                            == PassivaRacial.Tipo.FURIA_ANCESTRAL
                    && atacante.getPontosVidaAtuais()
                            <= atacante.getPontosVidaMaximos() * 0.25) {

                dano += passivaAtacante.getIntensidade();
            }

            alvo.receberDano(dano);
        }

        return new ResultadoAtaque(
                acertou,
                rolagem,
                valorAtaque,
                defesaAlvo,
                dano,
                tipoDano,
                alvo.getPontosVidaAtuais() <= 0
        );
    }

}