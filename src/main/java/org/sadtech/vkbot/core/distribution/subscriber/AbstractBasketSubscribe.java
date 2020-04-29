package org.sadtech.vkbot.core.distribution.subscriber;

import org.sadtech.vkbot.convert.Convert;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractBasketSubscribe<S, C> {

    private Set<AbstractBasketSubscribe> basketSubscribes;
    private AbstractBasketSubscribe prioritySubscribe;
    protected Convert<S, C> convert;

    public AbstractBasketSubscribe() {
        convert = (object) -> (C) object;
    }

    public abstract boolean check(S object);

    public void update(S object) {
        C newObject = convert.converting(object);
        if (!goNextSubscribe(newObject)) {
            processing(newObject);
        }
    }

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

    public Set<AbstractBasketSubscribe> getBasketSubscribes() {
        return basketSubscribes;
    }

    public void setBasketSubscribes(Set<AbstractBasketSubscribe> basketSubscribes) {
        this.basketSubscribes = basketSubscribes;
    }

    public AbstractBasketSubscribe getPrioritySubscribe() {
        return prioritySubscribe;
    }

    public void setPrioritySubscribe(AbstractBasketSubscribe prioritySubscribe) {
        this.prioritySubscribe = prioritySubscribe;
    }

    public Convert getConvert() {
        return convert;
    }

    public void setConvert(Convert convert) {
        this.convert = convert;
    }

}
