
package com.rpg.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rpg.catalogo.CatalogoClasses;
import com.rpg.catalogo.CatalogoRacas;
import com.rpg.entity.PersonagemEntity;
import com.rpg.model.Atributos;
import com.rpg.model.Classe;
import com.rpg.model.Personagem;
import com.rpg.model.Raca;
import com.rpg.repository.PersonagemRepository;

@Service
public class PersonagemService {

    private final PersonagemRepository personagemRepository;

    public PersonagemService(PersonagemRepository personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    @Transactional
    public PersonagemEntity criar(
            UUID jogadorId,
            String nome,
            String classeNome,
            String racaNome) {

        validarJogadorId(jogadorId);

        nome = validarTexto(nome, "O nome do personagem");
        classeNome = validarTexto(classeNome, "A classe do personagem");
        racaNome = validarTexto(racaNome, "A raça do personagem");

        Classe classe = CatalogoClasses.buscar(classeNome);
        Raca raca = CatalogoRacas.buscar(racaNome);

        Atributos atributosBase = classe.getAtributosBase();
        Atributos atributosFinais =
                atributosBase.somar(raca.getBonusAtributos());

        int vidaMaxima = classe.getPontosVidaBase();

        Personagem personagem = new Personagem(
                nome,
                classe,
                raca,
                atributosBase,
                atributosFinais,
                vidaMaxima
        );

        Atributos atributos = personagem.getAtributosFinais();

        PersonagemEntity entidade = new PersonagemEntity();

        entidade.setJogadorId(jogadorId);
        entidade.setNome(nome);
        entidade.setClasse(classe.getNome());
        entidade.setRaca(raca.getNome());
        entidade.setNivel(1);

        entidade.setForca(atributos.getForca());
        entidade.setDestreza(atributos.getDestreza());
        entidade.setConstituicao(atributos.getConstituicao());
        entidade.setInteligencia(atributos.getInteligencia());
        entidade.setSabedoria(atributos.getSabedoria());
        entidade.setCarisma(atributos.getCarisma());

        entidade.setVidaMaxima(personagem.getPontosVidaMaximos());
        entidade.setVidaAtual(personagem.getPontosVidaAtuais());

        return personagemRepository.save(entidade);
    }

    @Transactional(readOnly = true)
    public List<PersonagemEntity> listarPorJogador(UUID jogadorId) {
        validarJogadorId(jogadorId);
        return personagemRepository.findByJogadorId(jogadorId);
    }

    private void validarJogadorId(UUID jogadorId) {
        if (jogadorId == null) {
            throw new IllegalArgumentException(
                    "O jogador precisa estar identificado."
            );
        }
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                    campo + " não pode ficar vazio."
            );
        }

        return valor.trim();
    }
}
