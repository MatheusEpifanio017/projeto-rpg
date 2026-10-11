
package com.rpg.controller;

import com.rpg.entity.PersonagemEntity;
import com.rpg.service.PersonagemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemController {

    private final PersonagemService personagemService;

    public PersonagemController(
            PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody CriarPersonagemRequest request) {

        if (request == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "erro", "Os dados do personagem são obrigatórios."
                    ));
        }

        try {
            PersonagemEntity personagem = personagemService.criar(
                    request.jogadorId(),
                    request.nome(),
                    request.classe(),
                    request.raca()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(personagem);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam UUID jogadorId) {

        try {
            List<PersonagemEntity> personagens =
                    personagemService.listarPorJogador(jogadorId);

            return ResponseEntity.ok(personagens);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    public record CriarPersonagemRequest(
            UUID jogadorId,
            String nome,
            String classe,
            String raca
    ) {
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable UUID id,
            @RequestParam UUID jogadorId) {

        try {
            personagemService.excluir(jogadorId, id);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}
