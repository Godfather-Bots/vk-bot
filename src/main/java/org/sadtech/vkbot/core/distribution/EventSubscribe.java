package org.sadtech.vkbot.core.distribution;

public interface EventSubscribe<T> {

    void update(T object);

}
