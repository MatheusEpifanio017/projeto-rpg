
package com.rpg.historia;

import com.rpg.combate.CombateGuarda;
import com.rpg.service.SalvamentoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class Capitulo1Service {

    private final SalvamentoService salvamentoService;

    private final Map<UUID, CombateGuarda> combates =
            new HashMap<>();

    public Capitulo1Service(SalvamentoService salvamentoService) {
        this.salvamentoService = salvamentoService;
    }

    public EstadoHistoria iniciar(
            UUID jogadorId,
            UUID personagemId) {

        EstadoHistoria estado = salvamentoService.carregar(
                jogadorId, personagemId);

        if (!"capitulo_1".equals(estado.getCapituloAtual())) {
            estado.setCapituloAtual("capitulo_1");
            estado.setCenaAtual("cela");
            salvamentoService.salvar(
                    jogadorId, personagemId, estado);
        }

        return estado;
    }

    public String obterNarrativa(EstadoHistoria estado) {
        return switch (estado.getCenaAtual()) {
            case "cela" -> """
                    CAPÍTULO 1 — A Cela

                    Frio. Pedra. Escuridão.

                    A primeira coisa que sentes é a dureza
                    do chão contra o teu corpo. Quando abres
                    os olhos, encontras-te numa cela de pedra.

                    Não sabes o teu nome. Não sabes como
                    chegaste ali. As tuas memórias são apenas
                    fragmentos distantes.

                    Do outro lado das grades, um guarda
                    observa-te. Na sua armadura há um símbolo
                    dourado: um sol cercado por raios.

                    — Finalmente acordaste — diz ele,
                    aproximando-se das grades.
                    — Levanta-te. Tenho perguntas para ti.
                    """;

            case "combate_guarda" -> """
                    CENA 2 — O Guarda

                    O guarda abre a cela, mas mantém a mão
                    junto à espada.

                    — Não sei quem és, mas não vou arriscar
                    deixar-te andar livremente por aqui.

                    Ele desembainha a arma e assume uma
                    posição de combate.

                    O confronto é inevitável.
                    """;

            case "fortaleza" -> """
                    CENA 3 — A Fortaleza do Sol

                    Depois do confronto, deixas as masmorras
                    e chegas ao pátio de uma enorme fortaleza
                    coberta de neve.

                    Soldados atravessam o pátio. Todos usam
                    o símbolo do sol.

                    Perto de uma fogueira, ouves dois deles
                    a falar sobre um ataque ao Reino do Norte.

                    — Se as notícias forem verdadeiras,
                    a fronteira já caiu — murmura um soldado.

                    A conversa termina quando percebem
                    que estás a ouvir.
                    """;

            case "estabulos" -> """
                    CENA 4 — Os Estábulos

                    O vento corta o pátio quando encontras
                    os estábulos.

                    Entre os cavalos, um animal escuro
                    levanta a cabeça ao ouvir os teus passos.

                    Há selas preparadas para uma viagem,
                    mas ninguém parece esperar por ti.

                    Podes partir, mas ainda não sabes
                    para onde ir nem em quem confiar.
                    """;

            case "confronto" -> """
                    CENA 5 — O Emblema

                    Uma mulher de cabelos brancos surge
                    no caminho. A armadura negra que veste
                    está marcada por um floco de neve.

                    Os seus olhos descem até ao símbolo
                    solar na tua armadura.

                    — Onde conseguiste isso?

                    A voz dela é baixa, mas a mão repousa
                    perto da arma.

                    — Esse símbolo não devia estar contigo.
                    Diz-me quem és.
                    """;

            case "portoes" -> """
                    CENA 6 — Além dos Portões

                    Os portões da fortaleza abrem-se
                    diante de ti.

                    A neve cobre a estrada e esconde
                    o horizonte. Não tens memórias,
                    não tens respostas e não sabes
                    por que razão aquela mulher reconheceu
                    o símbolo na tua armadura.

                    Quando te afastas, um sino toca
                    atrás de ti.

                    A mulher dos cabelos brancos observa
                    a tua partida do alto das muralhas.

                    — Então começou — murmura ela.

                    FIM DO CAPÍTULO 1
                    """;

            default -> "Esta cena ainda não foi escrita.";
        };
    }

    public EstadoHistoria avancar(
            UUID jogadorId,
            UUID personagemId,
            EstadoHistoria estado) {

        String proximaCena = switch (estado.getCenaAtual()) {
            case "cela" -> "combate_guarda";
            case "combate_guarda" -> "fortaleza";
            case "fortaleza" -> "estabulos";
            case "estabulos" -> "confronto";
            case "confronto" -> "portoes";
            case "portoes" -> "portoes";
            default -> throw new IllegalStateException(
                    "Cena desconhecida: " + estado.getCenaAtual());
        };

        if ("combate_guarda".equals(estado.getCenaAtual())) {
            CombateGuarda combate = combates.get(personagemId);

            if (combate == null || !combate.isVencido()) {  
                throw new IllegalStateException(
                        "É necessário vencer o guarda antes de avançar.");
            }
        }

        estado.avancarCena(proximaCena);

        if ("portoes".equals(proximaCena)) {
            estado.registrarInformacao("capitulo_1_concluido");
        }

        salvamentoService.salvar(
                jogadorId, personagemId, estado);

        return estado;
    }

    public String iniciarCombate(UUID personagemId) {
        CombateGuarda combate = new CombateGuarda();
        combates.put(personagemId, combate);

        return combate.getEstado();
    }

    public String executarTurno(
            UUID personagemId,
            String acao) {

        CombateGuarda combate = combates.get(personagemId);

        if (combate == null) {
            throw new IllegalStateException(
                    "O combate ainda não foi iniciado.");
        }

        return combate.executarTurno(acao);
    }

    public boolean combateVencido(UUID personagemId) {
        CombateGuarda combate = combates.get(personagemId);
        return combate != null && combate.isVencido();
    }
}
