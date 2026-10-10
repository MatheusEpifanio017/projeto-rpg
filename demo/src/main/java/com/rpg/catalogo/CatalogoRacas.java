
package com.rpg.catalogo;

import java.util.List;
import java.util.Map;

import com.rpg.model.Atributos;
import com.rpg.model.PassivaRacial;
import com.rpg.model.Raca;

import static com.rpg.model.PassivaRacial.Tipo.*;

public final class CatalogoRacas {

    private CatalogoRacas() {
    }

    private static final Map<String, Raca> RACAS = Map.of(
        "Humano", new Raca(
            "Humano",
            new Atributos(1, 1, 1, 1, 1, 1),
            new PassivaRacial(
                "Adaptabilidade",
                "Recebe +1 em todos os atributos.",
                "Sempre ativa.",
                ADAPTABILIDADE,
                1
            )
        ),

        "Orc", new Raca(
            "Orc",
            new Atributos(2, 0, 1, 0, 0, 0),
            new PassivaRacial(
                "Fúria Ancestral",
                "Causa dano físico adicional quando está com pouca vida.",
                "Ativa quando os PV atuais são iguais ou inferiores "
                        + "a 25% dos PV máximos.",
                FURIA_ANCESTRAL,
                2
            )
        ),

        "Elfo", new Raca(
            "Elfo",
            new Atributos(0, 2, 0, 0, 1, 0),
            new PassivaRacial(
                "Reflexos Élficos",
                "Recebe bônus de iniciativa.",
                "Sempre ativa.",
                REFLEXOS_ELFICOS,
                2
            )
        ),

        "Anão", new Raca(
            "Anão",
            new Atributos(0, 0, 2, 0, 0, 0),
            new PassivaRacial(
                "Resiliência",
                "Reduz parte do dano recebido.",
                "Ativa ao receber dano.",
                RESILIENCIA,
                2
            )
        )
    );

    public static Raca buscar(String nome) {
        Raca raca = RACAS.get(nome);

        if (raca == null) {
            throw new IllegalArgumentException(
                    "Raça não encontrada: " + nome);
        }

        return raca;
    }

    public static List<Raca> listar() {
        return List.copyOf(RACAS.values());
    }
}
