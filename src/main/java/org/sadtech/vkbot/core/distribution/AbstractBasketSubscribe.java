package org.sadtech.vkbot.core.distribution;

import org.sadtech.vkbot.core.convert.Convert;

import java.util.Set;

public abstract class AbstractBasketSubscribe<S, C> {

    private Set<AbstractBasketSubscribe> basketSubscribes;
    private AbstractBasketSubscribe prioritySubscribe;
    protected Convert<S, C> convert;

    public AbstractBasketSubscribe() {
        convert = (object) -> (C) object;
    }

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

    protected abstract boolean check(S object);

    private boolean goNextSubscribe(C object) {
        boolean flag = false;
        if (prioritySubscribe != null && prioritySubscribe.check(object)) {
            prioritySubscribe.update(object);
            flag = true;
        } else if (basketSubscribes != null) {
            for (AbstractBasketSubscribe basketSubscribe : basketSubscribes) {
                if (basketSubscribe.check(object)) {
                    basketSubscribe.update(object);
                    flag = true;
                }
            }
        }
        return flag;
    }

    public void update(S object) {
        C newObject = convert.converting(object);
        if (!goNextSubscribe(newObject)) {
            processing(newObject);
        }
    }

    public abstract void processing(C object);
}
