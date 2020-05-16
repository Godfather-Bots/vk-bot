package org.sadtech.vk.bot.sdk.service.distribution.subscriber;

public interface BasketSubscribeService<S, C> {

    void update(S object);

    void processing(C object);

}
