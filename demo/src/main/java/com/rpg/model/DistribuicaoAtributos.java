
package com.rpg.model;

public class DistribuicaoAtributos {

    public static Atributos criar(String tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException(
                "A distribuição de atributos não pode ser nula."
            );
        }

        switch (tipo.toLowerCase()) {

            case "equilibrado":
                return new Atributos(
                    13, 13, 12, 12, 12, 10
                );

            case "combatente":
                return new Atributos(
                    15, 12, 14, 8, 10, 13
                );

            case "intelectual":
                return new Atributos(
                    8, 10, 12, 15, 14, 13
                );

            default:
                throw new IllegalArgumentException(
                    "Distribuição inválida: " + tipo
                    + ". Escolha: equilibrado, combatente ou intelectual."
                );
        }
    }
}
