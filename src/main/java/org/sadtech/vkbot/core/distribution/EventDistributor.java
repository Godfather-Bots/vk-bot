package org.sadtech.vkbot.core.distribution;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.service.RawEventService;

public class EventDistributor extends AbstractBasketSubscribe<JsonObject> implements Runnable {

    private static final Logger log = Logger.getLogger(EventDistributor.class);

    private final RawEventService rawEventService;

    public EventDistributor(RawEventService rawEventService) {
        this.rawEventService = rawEventService;
        log.info("EventDistributor инициализирован");
    }

    public void update() {
        while (true) {
            if (rawEventService.getJsonObjects().peek() != null) {
                JsonObject event = rawEventService.getJsonObjects().poll();
                log.info("Главный дистрибьютор отправил событие дальше");
                super.update(event);
            }
        }
    }

    @Override
    public void run() {
        log.info("EventDistributor запущен");
        update();
    }

    @Override
    protected boolean check(JsonObject object) {
        return false;
    }

    @Override
    public void processing(JsonObject object) {

    }
}
