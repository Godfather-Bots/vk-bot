package org.sadtech.vk.bot.sdk.repository.impl;

import com.google.gson.JsonObject;
import org.sadtech.vk.bot.sdk.repository.RawEventRepository;

import java.util.HashSet;
import java.util.Set;

public class RawEventRepositorySet implements RawEventRepository {

    private final Set<JsonObject> jsonObjects = new HashSet<>();

    @Override
    public void add(JsonObject jsonObject) {
        jsonObjects.add(jsonObject);
    }

    @Override
    public void cleanAll() {
        jsonObjects.clear();
    }

    @Override
    public Set<JsonObject> findNewEvent() {
        Set<JsonObject> copy = new HashSet<>(jsonObjects);
        jsonObjects.removeAll(copy);
        return copy;
    }
}
