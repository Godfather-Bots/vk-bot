package org.sadtech.vkbot.core.repository;

import com.google.gson.JsonObject;

import java.util.Set;

/**
 * Обработка событий социальной сети.
 *
 * @author upagge [08/07/2019]
 */
public interface RawEventRepository {

    void add(JsonObject dataObject);

    void cleanAll();

    Set<JsonObject> findNewEvent();

}
