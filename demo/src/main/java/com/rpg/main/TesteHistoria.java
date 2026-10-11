
package com.rpg.main;

import com.rpg.historia.EstadoHistoria;

public class TesteHistoria {

    public static void main(String[] args) {

        EstadoHistoria estado = new EstadoHistoria();

        System.out.println("Capitulo: "
                + estado.getCapituloAtual());

        System.out.println("Cena: "
                + estado.getCenaAtual());

        estado.registrarDecisao(
                "guarda_desacordado", true);

        estado.registrarInformacao(
                "plano_invasao_descoberto");

        System.out.println("\n=== ESTADO ATUAL ===");

        System.out.println("Guarda desacordado: "
                + estado.consultarDecisao(
                        "guarda_desacordado"));

        System.out.println("Plano descoberto: "
                + estado.descobriuInformacao(
                        "plano_invasao_descoberto"));

        estado.setCenaAtual("taverna");

        System.out.println("Nova cena: "
                + estado.getCenaAtual());
    }
}
    