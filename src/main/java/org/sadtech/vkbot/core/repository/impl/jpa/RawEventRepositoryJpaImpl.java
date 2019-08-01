package org.sadtech.vkbot.core.repository.impl.jpa;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.sadtech.vkbot.core.domain.jpa.JsonObjectId;
import org.sadtech.vkbot.core.repository.RawEventRepository;
import org.sadtech.vkbot.core.repository.jpa.RawEventRepositoryJpa;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: Добавить описание класса.
 *
 * @author upagge [28/07/2019]
 */
public class RawEventRepositoryJpaImpl implements RawEventRepository {

    private final RawEventRepositoryJpa rawEventRepositoryJpa;
    private final Gson gson = new Gson();
    private boolean addFlag = false;

    public RawEventRepositoryJpaImpl(RawEventRepositoryJpa rawEventRepositoryJpa) {
        this.rawEventRepositoryJpa = rawEventRepositoryJpa;
    }

    @Override
    public void add(JsonObject dataObject) {
        System.out.println(dataObject.toString());
        rawEventRepositoryJpa.saveAndFlush(new JsonObjectId(dataObject.toString()));
        addFlag = true;
    }

    @Override
    public void cleanAll() {
        rawEventRepositoryJpa.deleteAll();
    }

    @Override
    public Set<JsonObject> getEventQueue() {
        if (addFlag) {
            List<JsonObjectId> allEvent = rawEventRepositoryJpa.findAll();
            rawEventRepositoryJpa.deleteAll(allEvent);
            addFlag = false;
            return allEvent.stream().map(jsonObjectId -> convert(jsonObjectId.getJson())).collect(Collectors.toSet());
        } else {
            return null;
        }
    }

    private JsonObject convert(String json) {
        return gson.fromJson(json, JsonObject.class);
    }

}
