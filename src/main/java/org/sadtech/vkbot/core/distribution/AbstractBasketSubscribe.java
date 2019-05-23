package org.sadtech.vkbot.core.distribution;

import java.util.Set;

public abstract class AbstractBasketSubscribe<S> implements EventSubscribe<S> {

    private Set<AbstractBasketSubscribe> basketSubscribes;
    private AbstractBasketSubscribe prioritySubscribe;

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

    protected abstract boolean check(S object);

    private boolean goNextSubscribe(S object) {
        boolean flag = false;
        if (prioritySubscribe != null && prioritySubscribe.check(object)) {
            prioritySubscribe.update(object);
            flag = true;
        } else if (basketSubscribes != null) {
            for (AbstractBasketSubscribe basketSubscribe : basketSubscribes) {
                if (basketSubscribe.check(object)) {
                    basketSubscribe.update(object);
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public void update(S object) {
        if (!goNextSubscribe(object)) {
            processing(object);
        }
    }

    public abstract void processing(S object);
}
