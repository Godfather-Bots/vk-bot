package org.sadtech.vk.bot.sdk.service.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.vk.bot.sdk.repository.RawEventRepository;
import org.sadtech.vk.bot.sdk.service.RawEventService;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class RawEventServiceImpl implements RawEventService {

    private final RawEventRepository rawEventRepository;

    @Override
    public void cleanAll() {
        rawEventRepository.cleanAll();
        log.trace("Репозиторий событий очищен");
    }

    @Override
    public void add(JsonObject jsonObject) {
        rawEventRepository.add(jsonObject);
        log.trace("Событие отправленно в репозиторий");
    }

    @Override
    public Set<JsonObject> getNewEvent() {
        return rawEventRepository.findNewEvent();
    }

}
