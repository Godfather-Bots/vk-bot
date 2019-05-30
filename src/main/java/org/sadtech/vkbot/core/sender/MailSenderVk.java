package org.sadtech.vkbot.core.sender;

import com.google.gson.Gson;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.domain.BoxAnswer;
import org.sadtech.bot.core.service.sender.Sent;
import org.sadtech.vkbot.core.VkConnect;
import org.sadtech.vkbot.core.VkInsertData;
import org.sadtech.vkbot.core.convert.KeyBoardConvert;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class MailSenderVk implements Sent {

    private static final Logger log = Logger.getLogger(MailSenderVk.class);

    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;

    private final VkInsertData vkInsertData;
    private final Gson gson = new Gson();

    public MailSenderVk(VkConnect vkConnect) {
        this.vkApiClient = vkConnect.getVkApiClient();
        this.groupActor = vkConnect.getGroupActor();
        this.vkInsertData = new VkInsertData(vkConnect);
    }

    @Override
    public void send(Integer personId, BoxAnswer boxAnswer) {
        MessagesSendQuery messagesSendQuery = createMessage(boxAnswer, personId);
        sendMessage(messagesSendQuery);
    }

    @Override
    public void send(Integer integer, Integer integer1, BoxAnswer boxAnswer) {

    }

    private MessagesSendQuery createMessage(BoxAnswer boxAnswer, Integer peerId) {
        MessagesSendQuery messages = vkApiClient.messages().send(groupActor).peerId(peerId).message(vkInsertData.insertWords(boxAnswer.getMessage(), peerId)).randomId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        if (boxAnswer.getKeyboard() != null) {
            messages.keyboard(KeyBoardConvert.convertKeyboard(boxAnswer.getKeyboard()));
        } else {
            Keyboard keyboard = new Keyboard();
            keyboard.setOneTime(true);
            keyboard.setButtons(Collections.EMPTY_LIST);
            messages.keyboard(keyboard);
        }
        if (boxAnswer.getCoordinates() != null) {
            messages.lat(boxAnswer.getCoordinates().getLatitude()).lng(boxAnswer.getCoordinates().getLongitude());
        }
        if (boxAnswer.getStickerId() != null) {
            try {
                vkApiClient.messages().send(groupActor).peerId(peerId).stickerId(boxAnswer.getStickerId()).execute();
            } catch (ApiException | ClientException e) {
                log.error(e);
            }
        }
        return messages;
    }


    private void sendMessage(MessagesSendQuery messages) {
        try {
            messages.execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }


}
