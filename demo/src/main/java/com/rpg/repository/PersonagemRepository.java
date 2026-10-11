
package com.rpg.repository;

import com.rpg.entity.PersonagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonagemRepository
        extends JpaRepository<PersonagemEntity, UUID> {

    List<PersonagemEntity> findByJogadorId(UUID jogadorId);
}
