
package com.rpg.main;

import java.util.List;
import java.util.Scanner;

import com.rpg.catalogo.CatalogoClasses;
import com.rpg.catalogo.CatalogoRacas;
import com.rpg.service.CombateService;
import com.rpg.combate.ResultadoAtaque;
import com.rpg.combate.TipoDano;
import com.rpg.model.Classe;
import com.rpg.model.Personagem;
import com.rpg.model.Raca;
import com.rpg.service.CriacaoPersonagemService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CriacaoPersonagemService criacao =
                new CriacaoPersonagemService();

        System.out.println("================================");
        System.out.println("          EROSGAME RPG");
        System.out.println("     CRIACAO DE PERSONAGEM");
        System.out.println("================================");

        // Nome do personagem
        String nome;

        do {
            System.out.print("\nDigite o nome do personagem: ");
            nome = scanner.nextLine().trim();

            if (nome.isBlank()) {
                System.out.println(
                        "O nome nao pode ficar vazio.");
            }

        } while (nome.isBlank());

        // Escolha da classe
        List<Classe> classes = CatalogoClasses.listar();

        System.out.println("\n=== ESCOLHA SUA CLASSE ===");

        for (int i = 0; i < classes.size(); i++) {
            Classe classe = classes.get(i);

            System.out.println(
                    (i + 1) + " - " + classe.getNome());

            System.out.println(
                    "    " + classe.getDescricao());
        }

        int escolhaClasse = lerEscolha(
                scanner, classes.size());

        Classe classeEscolhida =
                classes.get(escolhaClasse - 1);

        // Escolha da raca
        List<Raca> racas = CatalogoRacas.listar();

        System.out.println("\n=== ESCOLHA SUA RACA ===");

        for (int i = 0; i < racas.size(); i++) {
            Raca raca = racas.get(i);

            System.out.println(
                    (i + 1) + " - " + raca.getNome());

            System.out.println(
                    "    Passiva: "
                    + raca.getPassiva().getNome());

            System.out.println(
                    "    " + raca.getPassiva().getDescricao());
        }

        int escolhaRaca = lerEscolha(
                scanner, racas.size());

        Raca racaEscolhida =
                racas.get(escolhaRaca - 1);

        // Criacao do personagem
        Personagem personagem = criacao.criar(
                nome,
                classeEscolhida.getNome(),
                racaEscolhida.getNome()
        );

        // Exibicao da ficha
        System.out.println("\n================================");
        System.out.println("       PERSONAGEM CRIADO");
        System.out.println("================================");

        System.out.println(
                "Nome: " + personagem.getNome());

        System.out.println(
                "Classe: "
                + personagem.getClasse().getNome());

        System.out.println(
                "Raca: " + personagem.getRaca().getNome());

        System.out.println(
                "Nivel: " + personagem.getNivel());

        System.out.println("\n=== ATRIBUTOS ===");

        System.out.println(
                "Forca: "
                + personagem.getAtributosFinais().getForca());

        System.out.println(
                "Destreza: "
                + personagem.getAtributosFinais().getDestreza());

        System.out.println(
                "Constituicao: "
                + personagem.getAtributosFinais()
                        .getConstituicao());

        System.out.println(
                "Inteligencia: "
                + personagem.getAtributosFinais()
                        .getInteligencia());

        System.out.println(
                "Sabedoria: "
                + personagem.getAtributosFinais()
                        .getSabedoria());

        System.out.println(
                "Carisma: "
                + personagem.getAtributosFinais()
                        .getCarisma());

        System.out.println("\n=== PONTOS DE VIDA ===");

        System.out.println(
                personagem.getPontosVidaAtuais()
                + "/" + personagem.getPontosVidaMaximos());

        System.out.println("\n=== PASSIVA RACIAL ===");

        System.out.println(
                personagem.getPassivaRacial().getNome());

        System.out.println(
                personagem.getPassivaRacial().getDescricao());

        System.out.println(
                "\nPersonagem criado com sucesso!");

        // Criacao do adversario
        Personagem adversario = criacao.criar(
                "Adversario",
                "Guerreiro",
                "Anão"
        );

        // Inicio do teste de combate
        CombateService combate = new CombateService();

        System.out.println("\n================================");
        System.out.println("        TESTE DE COMBATE");
        System.out.println("================================");

        System.out.println("\n=== COMBATENTES ===");

        System.out.println(
                personagem.getNome()
                + " - "
                + personagem.getClasse().getNome());

        System.out.println(
                "PV: "
                + personagem.getPontosVidaAtuais()
                + "/" + personagem.getPontosVidaMaximos());

        System.out.println();

        System.out.println(
                adversario.getNome()
                + " - "
                + adversario.getClasse().getNome());

        System.out.println(
                "PV: "
                + adversario.getPontosVidaAtuais()
                + "/" + adversario.getPontosVidaMaximos());

        // Rolagem de iniciativa
        int iniciativaJogador =
                combate.rolarIniciativa(personagem);

        int iniciativaAdversario =
                combate.rolarIniciativa(adversario);

        System.out.println("\n=== INICIATIVA ===");

        System.out.println(
                personagem.getNome()
                + ": " + iniciativaJogador);

        System.out.println(
                adversario.getNome()
                + ": " + iniciativaAdversario);

        if (iniciativaJogador > iniciativaAdversario) {
            System.out.println(
                    personagem.getNome()
                    + " tem a maior iniciativa!");
        } else if (iniciativaAdversario > iniciativaJogador) {
            System.out.println(
                    adversario.getNome()
                    + " tem a maior iniciativa!");
        } else {
            System.out.println("Empate na iniciativa!");
        }

        // Escolha do ataque
        System.out.println("\n=== ESCOLHA SEU ATAQUE ===");
        System.out.println("1 - Ataque fisico");
        System.out.println("2 - Ataque magico");

        int escolhaAtaque = lerEscolha(scanner, 2);

        TipoDano tipoDano;

        if (escolhaAtaque == 1) {
            tipoDano = TipoDano.FISICO;
        } else {
            tipoDano = TipoDano.MAGICO;
        }

        // Executa o ataque
        ResultadoAtaque resultado = combate.atacar(
                personagem,
                adversario,
                tipoDano
        );

        // Exibe o resultado
        System.out.println("\n=== RESULTADO DO ATAQUE ===");

        System.out.println(
                "Tipo de dano: " + resultado.getTipoDano());

        System.out.println(
                "Resultado do d20: "
                + resultado.getRolagem());

        System.out.println(
                "Valor total do ataque: "
                + resultado.getValorAtaque());

        System.out.println(
                "Defesa do adversario: "
                + resultado.getDefesaAlvo());

        if (resultado.isAcertou()) {
            System.out.println("\nO ataque acertou!");

            System.out.println(
                    "Dano causado: " + resultado.getDano());
        } else {
            System.out.println("\nO ataque errou!");
        }

        // Estado final
        System.out.println("\n=== ESTADO FINAL ===");

        System.out.println(
                personagem.getNome()
                + " - PV: "
                + personagem.getPontosVidaAtuais()
                + "/" + personagem.getPontosVidaMaximos());

        System.out.println(
                adversario.getNome()
                + " - PV: "
                + adversario.getPontosVidaAtuais()
                + "/" + adversario.getPontosVidaMaximos());

        if (resultado.isAlvoDerrotado()) {
            System.out.println(
                    "\nO adversario foi derrotado!");
        } else {
            System.out.println(
                    "\nO combate de teste terminou.");
        }

        scanner.close();
    }

    private static int lerEscolha(
            Scanner scanner,
            int quantidadeOpcoes) {

        while (true) {
            System.out.print(
                    "\nDigite o numero da sua escolha: ");

            String entrada = scanner.nextLine().trim();

            try {
                int escolha = Integer.parseInt(entrada);

                if (escolha >= 1
                        && escolha <= quantidadeOpcoes) {
                    return escolha;
                }

                System.out.println(
                        "Escolha um numero entre 1 e "
                        + quantidadeOpcoes + ".");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada invalida. "
                        + "Digite apenas um numero.");
            }
        }
    }
}
