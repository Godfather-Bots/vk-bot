package org.sadtech.vkbot.core.distribution;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.vkbot.core.distribution.subscriber.AbstractBasketSubscribe;
import org.sadtech.vkbot.core.service.RawEventService;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class EventDistributor implements Runnable {

    private final RawEventService rawEventService;
    private final Set<AbstractBasketSubscribe<JsonObject, ?>> basketSubscribes;

    @Override
    public void run() {
        while (true) {
            Optional.ofNullable(rawEventService.getNewEvent())
                    .ifPresent(events -> events.forEach(this::goNextSubscribe));
        }
    }

    private void goNextSubscribe(JsonObject object) {
        basketSubscribes.stream()
                .filter(basketSubscribe -> basketSubscribe.check(object))
                .forEach(basketSubscribe -> basketSubscribe.update(object));
    }
}
