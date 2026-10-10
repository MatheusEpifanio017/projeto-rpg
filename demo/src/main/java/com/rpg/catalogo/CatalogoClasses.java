
package com.rpg.catalogo;

import java.util.List;
import java.util.Map;

import com.rpg.model.Atributos;
import com.rpg.model.Classe;

public final class CatalogoClasses {

    private CatalogoClasses() {
    }

    private static final Map<String, Classe> CLASSES = Map.of(
        "Guerreiro", new Classe(
            "Guerreiro",
            "Especialista em combate físico e armas.",
            new Atributos(15, 12, 15, 10, 11, 10),
            12,
            List.of("Ataque Básico", "Postura Defensiva")
        ),

        "Feiticeiro", new Classe(
            "Feiticeiro",
            "Usa magia inata para lançar poderosos feitiços.",
            new Atributos(8, 13, 12, 12, 10, 16),
            6,
            List.of("Rajada Arcana", "Escudo Mágico")
        ),

        "Clérigo", new Classe(
            "Clérigo",
            "Usa poderes sagrados para curar e proteger.",
            new Atributos(12, 10, 14, 10, 16, 12),
            9,
            List.of("Luz Sagrada", "Cura Menor")
        )
    );

    public static Classe buscar(String nome) {
        Classe classe = CLASSES.get(nome);

        if (classe == null) {
            throw new IllegalArgumentException(
                    "Classe não encontrada: " + nome);
        }

        return classe;
    }

    public static List<Classe> listar() {
        return List.copyOf(CLASSES.values());
    }
}
