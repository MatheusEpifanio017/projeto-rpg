package com.rpg.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.historia.EstadoHistoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SalvamentoService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Path pasta = Path.of("saves");
    private final Path arquivo = pasta.resolve("progresso.json");

    public void salvar(EstadoHistoria estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "O estado da história não pode ser nulo.");
        }

        try {
            Files.createDirectories(pasta);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(arquivo.toFile(), estado);

            System.out.println("Progresso salvo com sucesso!");

        } catch (IOException e) {
            throw new RuntimeException(
                    "Não foi possível salvar o progresso.", e);
        }
    }

    public EstadoHistoria carregar() {
        if (!Files.exists(arquivo)) {
            System.out.println("Nenhum salvamento encontrado.");
            return new EstadoHistoria();
        }

        try {
            return mapper.readValue(
                    arquivo.toFile(),
                    EstadoHistoria.class);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Não foi possível carregar o progresso.", e);
        }
    }
}