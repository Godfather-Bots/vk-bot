package org.sadtech.vkbot.core.listener;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.LongPollServerKeyExpiredException;
import com.vk.api.sdk.objects.callback.longpoll.responses.GetLongPollEventsResponse;
import com.vk.api.sdk.objects.groups.LongPollServer;
import org.apache.log4j.Logger;
import org.sadtech.social.core.repository.impl.EventRepositoryQueue;
import org.sadtech.social.core.service.RawEventService;
import org.sadtech.social.core.service.impl.RawEventServiceImpl;
import org.sadtech.vkbot.core.config.VkConnect;

public class EventListenerVk implements Runnable {

    private static final Logger log = Logger.getLogger(EventListenerVk.class);

    private final VkApiClient vk;
    private final GroupActor actor;
    private static final Integer DEFAULT_WAIT_TIME = 25;
    private final RawEventService rawEventService;

    public EventListenerVk(VkConnect vkConnect) {
        vk = vkConnect.getVkApiClient();
        actor = vkConnect.getGroupActor();
        rawEventService = new RawEventServiceImpl(new EventRepositoryQueue());
    }

    public EventListenerVk(VkConnect vkConnect, RawEventService rawEventService) {
        vk = vkConnect.getVkApiClient();
        actor = vkConnect.getGroupActor();
        this.rawEventService = rawEventService;
    }

    public void listen() throws ClientException, ApiException {
        LongPollServer longPollServer = getLongPollServer();
        int lastTimeStamp = Integer.parseInt(longPollServer.getTs());
        while (true) {
            try {
                GetLongPollEventsResponse eventsResponse = vk.longPoll()
                        .getEvents(longPollServer.getServer(), longPollServer.getKey(), lastTimeStamp)
                        .waitTime(DEFAULT_WAIT_TIME)
                        .execute();
                eventsResponse.getUpdates().parallelStream().forEach(object -> {
                    log.info("Новое событие от LongPoll\n" + object);
                    rawEventService.add(object);
                });
                lastTimeStamp = eventsResponse.getTs();
            } catch (LongPollServerKeyExpiredException e) {
                log.error(e.getMessage());
                longPollServer = getLongPollServer();
            } catch (Exception e) {
                log.error(e.getMessage());
                break;
            }
        }
    }

    private LongPollServer getLongPollServer() throws ClientException, ApiException {
        log.info("LongPoll сервер инициализирован");
        if (actor != null) {
            return vk.groups().getLongPollServer(actor, actor.getGroupId()).execute();
        } else {
            throw new NullPointerException("Group actor");
        }
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (ClientException | ApiException e) {
            log.error(e.getMessage());
        }
    }

    public RawEventService getRawEventService() {
        return rawEventService;
    }

}
