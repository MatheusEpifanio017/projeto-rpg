
package com.rpg.service;

import com.rpg.historia.EstadoHistoria;
import com.rpg.inventario.Inventario;
import com.rpg.model.Personagem;

public class EstadoJogoService {

    private EstadoHistoria historia;
    private Personagem personagem;
    private Inventario inventario;

    public EstadoJogoService() {
        this.historia = new EstadoHistoria();
        this.inventario = new Inventario();
    }

    public EstadoHistoria getHistoria() {
        return historia;
    }

    public void setHistoria(EstadoHistoria historia) {
        this.historia = historia;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
}
