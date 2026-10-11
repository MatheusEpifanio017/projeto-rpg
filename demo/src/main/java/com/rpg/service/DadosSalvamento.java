
package com.rpg.service;

import java.util.ArrayList;
import java.util.List;

import com.rpg.historia.EstadoHistoria;
import com.rpg.inventario.Item;

public class DadosSalvamento {

    private EstadoHistoria historia;
    private String nomePersonagem;
    private String nomeClasse;
    private String nomeRaca;
    private int nivel;
    private int pontosVidaAtuais;
    private List<Item> itens = new ArrayList<>();

    public DadosSalvamento() {
    }

    public EstadoHistoria getHistoria() {
        return historia;
    }

    public void setHistoria(EstadoHistoria historia) {
        this.historia = historia;
    }

    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getNomeRaca() {
        return nomeRaca;
    }

    public void setNomeRaca(String nomeRaca) {
        this.nomeRaca = nomeRaca;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPontosVidaAtuais() {
        return pontosVidaAtuais;
    }

    public void setPontosVidaAtuais(int pontosVidaAtuais) {
        this.pontosVidaAtuais = pontosVidaAtuais;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens == null
                ? new ArrayList<>()
                : new ArrayList<>(itens);
    }
}
