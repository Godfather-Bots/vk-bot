package org.sadtech.vk.bot.sdk.service.distribution.subscriber;

import lombok.Setter;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Setter
public abstract class AbstractBasketSubscribe<S, C> implements BasketSubscribeService<S, C> {

    private Set<AbstractBasketSubscribe> basketSubscribes;
    private AbstractBasketSubscribe prioritySubscribe;

    public abstract boolean check(S object);

    public void update(S object) {
        C newObject = convert(object);
        if (!goNextSubscribe(newObject)) {
            processing(newObject);
        }
    }

    public abstract C convert(S object);

    private boolean goNextSubscribe(C object) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (prioritySubscribe != null && prioritySubscribe.check(object)) {
            prioritySubscribe.update(object);
            flag.set(true);
        } else if (basketSubscribes != null) {
            basketSubscribes.stream()
                    .filter(basketSubscribe -> basketSubscribe.check(object))
                    .forEach(basketSubscribe -> {
                        basketSubscribe.update(object);
                        flag.set(true);
                    });
        }
        return flag.get();
    }

    public abstract void processing(C object);

}
