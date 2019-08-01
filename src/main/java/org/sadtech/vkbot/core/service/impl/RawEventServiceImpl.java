package org.sadtech.vkbot.core.service.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.vkbot.core.repository.RawEventRepository;
import org.sadtech.vkbot.core.service.RawEventService;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class RawEventServiceImpl implements RawEventService {

    private final RawEventRepository rawEventRepository;

    @Override
    public void cleanAll() {
        rawEventRepository.cleanAll();
        log.info("Репозиторий событий очищен");
    }

    @Override
    public void add(JsonObject jsonObject) {
        rawEventRepository.add(jsonObject);
        log.info("Событие отправленно в репозиторий");
    }

    @Override
    public Set<JsonObject> getNewEvent() {
        return rawEventRepository.findNewEvent();
    }

}
