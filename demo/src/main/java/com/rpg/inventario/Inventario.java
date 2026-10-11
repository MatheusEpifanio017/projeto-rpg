
package com.rpg.inventario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventario {

    private final List<Item> itens = new ArrayList<>();

    public void adicionar(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "O item nao pode ser nulo.");
        }

        itens.add(item);
    }

    public boolean remover(String id) {
        return itens.removeIf(
                item -> item.getId().equals(id));
    }

    public boolean possui(String id) {
        return itens.stream()
                .anyMatch(item -> item.getId().equals(id));
    }

    public List<Item> listar() {
        return Collections.unmodifiableList(itens);
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public int quantidade() {
        return itens.size();
    }
}
