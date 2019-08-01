package org.sadtech.vkbot.core.distribution;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.vkbot.core.service.RawEventService;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class EventDistributor implements Runnable {

    private final RawEventService rawEventService;
    private final Set<AbstractBasketSubscribe> basketSubscribes;

    public EventDistributor(RawEventService rawEventService, Set<AbstractBasketSubscribe> basketSubscribes) {
        this.rawEventService = rawEventService;
        this.basketSubscribes = basketSubscribes;
        log.info("EventDistributor инициализирован");
    }

    @Override
    public void run() {
        while (true) {
            Optional.ofNullable(rawEventService.getJsonObjects())
                    .ifPresent(events -> events.forEach(this::goNextSubscribe));
        }
    }

    private boolean goNextSubscribe(JsonObject object) {
        AtomicBoolean flag = new AtomicBoolean(false);
        basketSubscribes.stream()
                .filter(basketSubscribe -> basketSubscribe.check(object))
                .forEach(basketSubscribe -> {
                    basketSubscribe.update(object);
                    flag.set(true);
                });
        return flag.get();
    }
}
