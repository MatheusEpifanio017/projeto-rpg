
package com.rpg.historia;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EstadoHistoria {

    private String capituloAtual;
    private String cenaAtual;
    
    private final Map<String, Boolean> decisoes;
    private final Map<String, Boolean> informacoesDescobertas;

    public EstadoHistoria() {
        this.capituloAtual = "capitulo_1";
        this.cenaAtual = "cela";

        this.decisoes = new HashMap<>();
        this.informacoesDescobertas = new HashMap<>();
    }

    public String getCapituloAtual() {
        return capituloAtual;
    }

    public void setCapituloAtual(String capituloAtual) {
        if (capituloAtual == null || capituloAtual.isBlank()) {
            throw new IllegalArgumentException(
                    "O capitulo nao pode ficar vazio.");
        }

        this.capituloAtual = capituloAtual;
    }

    public String getCenaAtual() {
        return cenaAtual;
    }

    public void setCenaAtual(String cenaAtual) {
        if (cenaAtual == null || cenaAtual.isBlank()) {
            throw new IllegalArgumentException(
                    "A cena nao pode ficar vazia.");
        }

        this.cenaAtual = cenaAtual;
    }
    
        public void setDecisoes(Map<String, Boolean> decisoes) {
        this.decisoes.clear();

        if (decisoes != null) {
        this.decisoes.putAll(decisoes);
        }
        }

        public void setInformacoesDescobertas(
        Map<String, Boolean> informacoesDescobertas) {

        this.informacoesDescobertas.clear();

        if (informacoesDescobertas != null) {
        this.informacoesDescobertas.putAll(
        informacoesDescobertas
        );
        }
        }


    public void registrarDecisao(
            String decisao, boolean resultado) {

        decisoes.put(decisao, resultado);
    }

    public boolean consultarDecisao(String decisao) {
        return decisoes.getOrDefault(decisao, false);
    }

    public void registrarInformacao(String informacao) {
        informacoesDescobertas.put(informacao, true);
    }

    public boolean descobriuInformacao(String informacao) {
        return informacoesDescobertas.getOrDefault(
                informacao, false);
    }

    public Map<String, Boolean> getDecisoes() {
        return Collections.unmodifiableMap(decisoes);
    }

    public Map<String, Boolean> getInformacoesDescobertas() {
        return Collections.unmodifiableMap(
                informacoesDescobertas);
    }
}
