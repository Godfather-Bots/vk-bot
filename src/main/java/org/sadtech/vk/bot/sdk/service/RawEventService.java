package org.sadtech.vk.bot.sdk.service;

import com.google.gson.JsonObject;

import java.util.Set;

/**
 * Интерфейс для взаимодействия с событиями социальной сети.
 *
 * @author upagge [08/07/2019]
 */
public interface RawEventService {

    void cleanAll();

    void add(JsonObject jsonObject);

    Set<JsonObject> getNewEvent();

}