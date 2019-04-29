package org.sadtech.vkbot.core.distribution;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.service.RawEventService;

import java.util.HashMap;
import java.util.Map;

public class EventDistributor implements Runnable {

    public static final Logger log = Logger.getLogger(EventDistributor.class);

    private RawEventService rawEventService;
    private Map<String, EventSubscribe> eventDistributionMap = new HashMap<>();

    public EventDistributor(RawEventService rawEventService) {
        this.rawEventService = rawEventService;
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
        update();
    }
}
