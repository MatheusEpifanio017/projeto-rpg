
package com.rpg.main;

import java.util.List;

import com.rpg.model.Atributos;
import com.rpg.model.Classe;
import com.rpg.model.DistribuicaoAtributos;
import com.rpg.model.Personagem;
import com.rpg.model.Subclasse;

public class Teste {

    public static void main(String[] args) {

        Atributos atributos =
                DistribuicaoAtributos.criar("combatente");

        Personagem personagem = new Personagem(
                "Theodore",
                "Humano",
                atributos
        );

        Classe guerreiro = new Classe(
                "Guerreiro",
                "Especialista em combate com espadas",
                "Forca",
                10
        );

        Subclasse mestreDeBatalha = new Subclasse(
                "Cavaleiro do Eco",
                "Guerreiro",
                "Invocador de clones sombrios",
                List.of("Invocar Eco")
        );

        personagem.escolherClasse(guerreiro);
        personagem.escolherSubclasse(mestreDeBatalha);

        System.out.println("Nome: " + personagem.getNome());
        System.out.println("Raça: " + personagem.getRaca());
        System.out.println("Classe: "
                + personagem.getClasse().getNome());
        System.out.println("Subclasse: "
                + personagem.getSubclasse().getNome());

        System.out.println("Força: "
                + atributos.getForca());
        System.out.println("Modificador de força: "
                + atributos.getModificadorForca());
    }
}
