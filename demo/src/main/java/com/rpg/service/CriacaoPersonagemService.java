
package com.rpg.service;

import com.rpg.catalogo.CatalogoClasses;
import com.rpg.catalogo.CatalogoRacas;
import com.rpg.model.Atributos;
import com.rpg.model.Classe;
import com.rpg.model.Personagem;
import com.rpg.model.Raca;

public class CriacaoPersonagemService {

    public Personagem criar(
            String nome,
            String nomeClasse,
            String nomeRaca) {

        Classe classe = CatalogoClasses.buscar(nomeClasse);
        Raca raca = CatalogoRacas.buscar(nomeRaca);

        Atributos atributosBase = classe.getAtributosBase();

        Atributos atributosFinais = atributosBase.somar(
                raca.getBonusAtributos());

        int modificadorConstituicao =
                atributosFinais.getModificadorConstituicao();

        int pontosVidaIniciais =
                classe.getPontosVidaBase()
                + modificadorConstituicao;

        pontosVidaIniciais = Math.max(1, pontosVidaIniciais);

        return new Personagem(
                nome,
                classe,
                raca,
                atributosBase,
                atributosFinais,
                pontosVidaIniciais
        );
    }
}
