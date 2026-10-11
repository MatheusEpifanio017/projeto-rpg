package com.rpg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.historia.EstadoHistoria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SalvamentoService {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper;

    public SalvamentoService(
            JdbcTemplate jdbcTemplate,
            ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Transactional
    public void salvar(
            UUID jogadorId,
            UUID personagemId,
            EstadoHistoria estado) {

        validarIdentificadores(jogadorId, personagemId);

        if (estado == null) {
            throw new IllegalArgumentException(
                    "O estado da história não pode ser nulo.");
        }

        try {
            String dadosJson = mapper.writeValueAsString(estado);

            Long salvamentoId = jdbcTemplate.query(
                    """
                    SELECT id
                    FROM salvamentos
                    WHERE jogador_id = ?
                      AND personagem_id = ?
                    ORDER BY atualizado_em DESC NULLS LAST
                    LIMIT 1
                    """,
                    rs -> rs.next() ? rs.getLong("id") : null,
                    jogadorId,
                    personagemId
            );

            if (salvamentoId != null) {
                jdbcTemplate.update(
                        """
                        UPDATE salvamentos
                        SET dados = CAST(? AS jsonb),
                            atualizado_em = CURRENT_TIMESTAMP
                        WHERE id = ?
                          AND jogador_id = ?
                          AND personagem_id = ?
                        """,
                        dadosJson,
                        salvamentoId,
                        jogadorId,
                        personagemId
                );

            } else {
                jdbcTemplate.update(
                        """
                        INSERT INTO salvamentos (
                            jogador_id,
                            personagem_id,
                            nome_salvamento,
                            dados,
                            criado_em,
                            atualizado_em
                        )
                        VALUES (
                            ?, ?, ?, CAST(? AS jsonb),
                            CURRENT_TIMESTAMP,
                            CURRENT_TIMESTAMP
                        )
                        """,
                        jogadorId,
                        personagemId,
                        "Aventura principal",
                        dadosJson
                );
            }

        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Não foi possível converter o estado da história em JSON.",
                    e);
        }
    }

    public EstadoHistoria carregar(
            UUID jogadorId,
            UUID personagemId) {

        validarIdentificadores(jogadorId, personagemId);

        return jdbcTemplate.query(
                """
                SELECT dados::text AS dados
                FROM salvamentos
                WHERE jogador_id = ?
                  AND personagem_id = ?
                ORDER BY atualizado_em DESC NULLS LAST
                LIMIT 1
                """,
                rs -> {
                    if (!rs.next()) {
                        return new EstadoHistoria();
                    }

                    try {
                        return mapper.readValue(
                                rs.getString("dados"),
                                EstadoHistoria.class);

                    } catch (JsonProcessingException e) {
                        throw new IllegalStateException(
                                "Não foi possível carregar o estado da história.",
                                e);
                    }
                },
                jogadorId,
                personagemId
        );
    }

    private void validarIdentificadores(
            UUID jogadorId,
            UUID personagemId) {

        if (jogadorId == null || personagemId == null) {
            throw new IllegalArgumentException(
                    "O jogador e o personagem são obrigatórios.");
        }
    }
}   