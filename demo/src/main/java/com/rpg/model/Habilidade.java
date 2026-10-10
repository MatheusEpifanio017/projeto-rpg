
package com.rpg.model;

public record Habilidade(
        String nome,
        String descricao,
        int nivelDesbloqueio, 
        int custoRecurso
) {
    public Habilidade {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "A habilidade precisa de um nome.");
        }

        if (nivelDesbloqueio < 1 || nivelDesbloqueio > 20) {
            throw new IllegalArgumentException(
                    "O nível deve estar entre 1 e 20.");
        }

        if (custoRecurso < 0) {
            throw new IllegalArgumentException(
                    "O custo não pode ser negativo.");
        }
    }
}
