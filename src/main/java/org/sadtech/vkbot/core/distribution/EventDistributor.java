package org.sadtech.vkbot.core.distribution;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.service.RawEventService;

import java.util.HashMap;
import java.util.Map;

public class EventDistributor implements Runnable {

    private static final Logger log = Logger.getLogger(EventDistributor.class);

    private final RawEventService rawEventService;
    private final Map<String, EventSubscribe> eventDistributionMap = new HashMap<>();

    public EventDistributor(RawEventService rawEventService) {
        this.rawEventService = rawEventService;
        log.info("EventDistributor инициализирован");
    }

    public void update() {
        while (true) {
            try {
                if (rawEventService.getJsonObjects().peek() != null) {
                    JsonObject event = rawEventService.getJsonObjects().poll();
                    log.info("Главный дистрибьютор отправил событие дальше");
                    if (eventDistributionMap.containsKey(event.get("type").getAsString())) {
                        eventDistributionMap.get(event.get("type").getAsString()).update(event.getAsJsonObject("object"));
                    }
                }
            } catch (Exception e) {
                log.error(e.getStackTrace());
                break;
            }
        }
    }

    public void setEventDistributionMap(String key, EventSubscribe eventSubscribe) {
        this.eventDistributionMap.put(key, eventSubscribe);
    }

    @Override
    public void run() {
        log.info("EventDistributor запущен");
        update();
    }
}
