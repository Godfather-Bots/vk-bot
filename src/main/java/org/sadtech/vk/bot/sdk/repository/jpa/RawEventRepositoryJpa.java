package org.sadtech.vk.bot.sdk.repository.jpa;

import org.sadtech.vk.bot.sdk.domain.jpa.JsonObjectId;
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
