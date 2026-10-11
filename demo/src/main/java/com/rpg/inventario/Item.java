
package com.rpg.inventario;

public class Item {

    private final String id;
    private final String nome;
    private final String descricao;
    private final String tipo;

    public Item(
            String id,
            String nome,
            String descricao,
            String tipo) {

        if (id == null || id.isBlank()
                || nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O item precisa ter ID e nome.");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }
}
