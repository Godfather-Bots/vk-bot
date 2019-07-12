package org.sadtech.vkbot.core.sender;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import org.apache.log4j.Logger;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.domain.keyboard.KeyBoard;
import org.sadtech.social.core.service.sender.Sent;
import org.sadtech.vkbot.core.config.VkConnect;
import org.sadtech.vkbot.core.convert.KeyBoardConvert;
import org.sadtech.vkbot.core.utils.VkInsertData;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class MailSenderVk implements Sent {

    private static final Logger log = Logger.getLogger(MailSenderVk.class);

    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final VkInsertData vkInsertData;
    private final KeyBoardConvert keyBoardConvert;

    public MailSenderVk(VkConnect vkConnect) {
        this.vkApiClient = vkConnect.getVkApiClient();
        this.groupActor = vkConnect.getGroupActor();
        this.vkInsertData = new VkInsertData(vkConnect);
        keyBoardConvert = new KeyBoardConvert(vkConnect);
    }

    @Override
    public void send(Integer personId, BoxAnswer boxAnswer) {
        MessagesSendQuery messagesSendQuery = createMessage(boxAnswer, personId);
        sendMessage(messagesSendQuery);
    }

    private MessagesSendQuery createMessage(BoxAnswer boxAnswer, Integer peerId) {
        MessagesSendQuery messages = vkApiClient.messages().send(groupActor).peerId(peerId)
                .message(vkInsertData.insertWords(boxAnswer.getMessage(), peerId))
                .randomId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        messages.keyboard(convertKeyBoard(boxAnswer.getKeyboard()));

        if (boxAnswer.getCoordinates() != null) {
            messages.lat(boxAnswer.getCoordinates().getLatitude()).lng(boxAnswer.getCoordinates().getLongitude());
        }
        if (boxAnswer.getStickerId() != null) {
            try {
                vkApiClient.messages().send(groupActor).peerId(peerId).stickerId(boxAnswer.getStickerId())
                        .randomId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE)).execute();
            } catch (ApiException | ClientException e) {
                log.error(e.getMessage());
            }
        }
        return messages;
    }

    private Keyboard convertKeyBoard(KeyBoard keyboard) {
        if (keyboard != null) {
            return keyBoardConvert.convertKeyboard(keyboard);
        } else {
            Keyboard keyboardVk = new Keyboard();
            keyboardVk.setOneTime(true);
            keyboardVk.setButtons(Collections.EMPTY_LIST);
            return keyboardVk;
        }
    }


    private void sendMessage(MessagesSendQuery messages) {
        try {
            messages.execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    @Override
    public void send(Integer integer, Integer integer1, BoxAnswer boxAnswer) {

    }

}
