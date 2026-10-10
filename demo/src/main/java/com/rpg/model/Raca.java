
package com.rpg.model;

import java.util.Collections;
import java.util.Map;
import java.util.List;

public class Raca {

    private final String nome;
    private final Map<String, Integer> bonusAtributos;
    private final List<Caracteristica> caracteristicas;

    public Raca(
            String nome,
            Map<String, Integer> bonusAtributos,
            List<Caracteristica> caracteristicas) {

        this.nome = nome;
        this.bonusAtributos =
                Map.copyOf(bonusAtributos);
        this.caracteristicas =
                List.copyOf(caracteristicas);
    }

    public String getNome() {
        return nome;
    }

    public Map<String, Integer> getBonusAtributos() {
        return Collections.unmodifiableMap(bonusAtributos);
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }
}
