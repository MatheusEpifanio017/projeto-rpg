
package com.rpg.service;

import com.rpg.entity.PersonagemEntity;
import com.rpg.repository.PersonagemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
            String classe,
            String raca
    ) {
        validarJogadorId(jogadorId);

        nome = validarTexto(nome, "O nome do personagem");
        classe = validarTexto(classe, "A classe do personagem");
        raca = validarTexto(raca, "A raça do personagem");

        PersonagemEntity personagem = new PersonagemEntity();

        personagem.setJogadorId(jogadorId);
        personagem.setNome(nome);
        personagem.setClasse(classe);
        personagem.setRaca(raca);
        personagem.setNivel(1);

        return personagemRepository.save(personagem);
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
