package org.sadtech.vkbot.core.listener;

import com.google.gson.JsonObject;
import com.vk.api.sdk.callback.longpoll.responses.GetLongPollEventsResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.LongPollServerKeyExpiredException;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.repository.impl.EventRepositoryQueue;
import org.sadtech.bot.core.service.RawEventService;
import org.sadtech.bot.core.service.impl.RawEventServiceImpl;
import org.sadtech.vkbot.core.VkConnect;

public class EventListenerVk implements Runnable {

    public static final Logger log = Logger.getLogger(EventListenerVk.class);

    private VkApiClient vk;
    private GroupActor actor;

    private static final Integer DEFAULT_WAIT_TIME = 25;

    private RawEventService rawEventService;

    public EventListenerVk(VkConnect vkConnect) {
        vk = vkConnect.getVkApiClient();
        actor = vkConnect.getGroupActor();
        rawEventService = new RawEventServiceImpl(new EventRepositoryQueue());
    }

    public EventListenerVk(VkConnect vkConnect, RawEventService rawEventService) {
        this.vk = vkConnect.getVkApiClient();
        this.actor = vkConnect.getGroupActor();
        this.rawEventService = rawEventService;
    }

    public RawEventService getRawEventService() {
        return rawEventService;
    }

    public void listen() throws ClientException, ApiException {
        GetLongPollServerResponse longPollServer = getLongPollServer();
        int lastTimeStamp = longPollServer.getTs();
        while (true) {
            try {
                GetLongPollEventsResponse eventsResponse = vk.longPoll().getEvents(longPollServer.getServer(), longPollServer.getKey(), lastTimeStamp).waitTime(DEFAULT_WAIT_TIME).execute();
                for (JsonObject jsonObject : eventsResponse.getUpdates()) {
                    log.info("Новое событие от LongPoll\n" + jsonObject);
                    rawEventService.add(jsonObject);
                }
                lastTimeStamp = eventsResponse.getTs();
            } catch (LongPollServerKeyExpiredException e) {
                log.error(e.getStackTrace());
                longPollServer = getLongPollServer();
            } catch (Exception e) {
                log.error(e.getStackTrace());
                break;
            }
        }
    }

    private GetLongPollServerResponse getLongPollServer() throws ClientException, ApiException {
        log.info("LongPoll сервер инициализирован");
        if (actor != null) {
            return vk.groups().getLongPollServer(actor).execute();
        } else {
            throw new NullPointerException("Group actor");
        }
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (ClientException | ApiException e) {
            log.error(e);
        }
    }

}
