
package com.rpg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "personagens", schema = "public")
public class PersonagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "jogador_id", nullable = false)
    private UUID jogadorId;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "classe", nullable = false)
    private String classe;

    @Column(name = "raca", nullable = false)
    private String raca;

    @Column(name = "nivel", nullable = false)
    private Integer nivel = 1;

    @Column(name = "forca", nullable = false)
    private Integer forca;

    @Column(name = "destreza", nullable = false)
    private Integer destreza;

    @Column(name = "constituicao", nullable = false)
    private Integer constituicao;

    @Column(name = "inteligencia", nullable = false)
    private Integer inteligencia;

    @Column(name = "sabedoria", nullable = false)
    private Integer sabedoria;

    @Column(name = "carisma", nullable = false)
    private Integer carisma;

    @Column(name = "vida_maxima", nullable = false)
    private Integer vidaMaxima;

    @Column(name = "vida_atual", nullable = false)
    private Integer vidaAtual;

    public PersonagemEntity() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(UUID jogadorId) {
        this.jogadorId = jogadorId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getForca() {
        return forca;
    }

    public void setForca(Integer forca) {
        this.forca = forca;
    }

    public Integer getDestreza() {
        return destreza;
    }

    public void setDestreza(Integer destreza) {
        this.destreza = destreza;
    }

    public Integer getConstituicao() {
        return constituicao;
    }

    public void setConstituicao(Integer constituicao) {
        this.constituicao = constituicao;
    }

    public Integer getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(Integer inteligencia) {
        this.inteligencia = inteligencia;
    }

    public Integer getSabedoria() {
        return sabedoria;
    }

    public void setSabedoria(Integer sabedoria) {
        this.sabedoria = sabedoria;
    }

    public Integer getCarisma() {
        return carisma;
    }

    public void setCarisma(Integer carisma) {
        this.carisma = carisma;
    }

    public Integer getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(Integer vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public Integer getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(Integer vidaAtual) {
        this.vidaAtual = vidaAtual;
    }
}
