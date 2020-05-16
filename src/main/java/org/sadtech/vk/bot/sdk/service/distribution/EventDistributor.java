package org.sadtech.vk.bot.sdk.service.distribution;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.vk.bot.sdk.service.RawEventService;
import org.sadtech.vk.bot.sdk.service.distribution.subscriber.AbstractBasketSubscribe;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class EventDistributor {

    private final RawEventService rawEventService;
    private final Set<AbstractBasketSubscribe<JsonObject, ?>> basketSubscribes;

    public void run() {
        rawEventService.getNewEvent().forEach(this::goNextSubscribe);
    }

    private void goNextSubscribe(JsonObject object) {
        basketSubscribes.stream()
                .filter(basketSubscribe -> basketSubscribe.check(object))
                .forEach(basketSubscribe -> basketSubscribe.update(object));
    }

}
