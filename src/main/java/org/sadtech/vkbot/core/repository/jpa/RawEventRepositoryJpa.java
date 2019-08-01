package org.sadtech.vkbot.core.repository.jpa;

import org.sadtech.vkbot.core.domain.jpa.JsonObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO: Добавить описание интерфейса.
 *
 * @author upagge [28/07/2019]
 */
@Repository
public interface RawEventRepositoryJpa extends JpaRepository<JsonObjectId, Integer> {

}
