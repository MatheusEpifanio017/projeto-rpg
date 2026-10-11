
package com.rpg.main;

import com.rpg.historia.EstadoHistoria;
import com.rpg.service.SalvamentoService;

public class TesteSalvamento {

    public static void main(String[] args) {
        SalvamentoService salvamento = new SalvamentoService();

        EstadoHistoria estado = new EstadoHistoria();

        estado.setCenaAtual("taverna");
        estado.registrarDecisao("confiou_na_guerreira", true);
        estado.registrarInformacao("invasao_do_norte");

        salvamento.salvar(estado);

        EstadoHistoria carregado = salvamento.carregar();

        System.out.println("Capítulo: " + carregado.getCapituloAtual());
        System.out.println("Cena: " + carregado.getCenaAtual());

        System.out.println(
            "Confiou na guerreira: "
            + carregado.consultarDecisao("confiou_na_guerreira")
        );

        System.out.println(
            "Descobriu a invasão: "
            + carregado.descobriuInformacao("invasao_do_norte")
        );
    }
}
