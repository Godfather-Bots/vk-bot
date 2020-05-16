package org.sadtech.vk.bot.sdk.sender;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.domain.keyboard.KeyBoard;
import org.sadtech.social.core.service.sender.SendType;
import org.sadtech.social.core.service.sender.Sending;
import org.sadtech.vk.bot.sdk.config.VkConnect;
import org.sadtech.vk.bot.sdk.utils.VkInsertData;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class MailSenderVk implements Sending {

    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final VkInsertData vkInsertData;

    @Setter
    private ConversionService conversionService;

    public MailSenderVk(VkConnect vkConnect) {
        this.vkApiClient = vkConnect.getVkApiClient();
        this.groupActor = vkConnect.getGroupActor();
        this.vkInsertData = new VkInsertData(vkConnect);
    }

    @Override
    public void send(Long personId, BoxAnswer boxAnswer) {
        MessagesSendQuery messagesSendQuery = createMessage(boxAnswer, personId);
        sendMessage(messagesSendQuery);
    }

    private MessagesSendQuery createMessage(BoxAnswer boxAnswer, Long peerId) {
        MessagesSendQuery messages = vkApiClient.messages().send(groupActor).peerId(peerId.intValue())
                .message(vkInsertData.insertWords(boxAnswer.getMessage(), peerId))
                .randomId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        messages.keyboard(convertKeyBoard(boxAnswer.getKeyBoard()));

        if (boxAnswer.getCoordinates() != null) {
            messages.lat(boxAnswer.getCoordinates().getLatitude()).lng(boxAnswer.getCoordinates().getLongitude());
        }
        if (boxAnswer.getStickerId() != null) {
            try {
                vkApiClient.messages().send(groupActor).peerId(peerId.intValue()).stickerId(boxAnswer.getStickerId())
                        .randomId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE)).execute();
            } catch (ApiException | ClientException e) {
                log.error(e.getMessage());
            }
        }
        return messages;
    }

    private Keyboard convertKeyBoard(KeyBoard keyboard) {
        if (keyboard != null) {
            return conversionService.convert(keyboard, Keyboard.class);
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
            log.error(e.getMessage());
        }
    }

    @Override
    public void send(Long integer, Long integer1, BoxAnswer boxAnswer) {

    }

    @Override
    public SendType getType() {
        return SendType.PRIVATE;
    }

}
