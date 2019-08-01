package org.sadtech.vkbot.core.repository.impl;

import com.google.gson.JsonObject;
import org.sadtech.vkbot.core.repository.RawEventRepository;

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

    public Set<JsonObject> findNewEvent() {
        return jsonObjects;
    }
}
