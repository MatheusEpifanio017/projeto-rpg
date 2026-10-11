
package com.rpg.main;

import com.rpg.inventario.Inventario;
import com.rpg.inventario.Item;

public class TesteInventario {

    public static void main(String[] args) {

        Inventario inventario = new Inventario();

        Item uniforme = new Item(
                "uniforme_sul",
                "Uniforme do Sul",
                "Uniforme com o simbolo de um sol.",
                "VESTIMENTA"
        );

        Item chave = new Item(
                "chave_enferrujada",
                "Chave enferrujada",
                "Uma chave antiga encontrada na cela.",
                "OBJETO"
        );

        inventario.adicionar(uniforme);
        inventario.adicionar(chave);

        System.out.println("=== INVENTARIO ===");

        for (Item item : inventario.listar()) {
            System.out.println(item.getNome());
            System.out.println(item.getDescricao());
        }

        System.out.println(
                "\nQuantidade: " + inventario.quantidade());

        System.out.println(
                "Possui a chave? "
                + inventario.possui("chave_enferrujada"));

        inventario.remover("chave_enferrujada");

        System.out.println(
                "Quantidade apos remover a chave: "
                + inventario.quantidade());
    }
}
