package org.sadtech.vkbot.core.sender;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.domain.keyboard.KeyBoardButton;
import org.sadtech.social.core.domain.keyboard.KeyBoardLine;
import org.sadtech.social.core.domain.keyboard.button.KeyBoardButtonText;
import org.sadtech.social.core.exception.MailSendException;
import org.sadtech.social.core.service.sender.SendType;
import org.sadtech.social.core.service.sender.Sending;
import org.sadtech.vkbot.core.config.VkConnect;
import org.sadtech.vkbot.core.utils.VkInsertData;

public class BoardCommentSenderVk implements Sending {

    private final VkApiClient vkApiClient;
    private final GroupActor groupActor;
    private final UserActor userActor;
    private final VkInsertData vkInsertData;

    public BoardCommentSenderVk(VkConnect vkConnect) {
        this.vkApiClient = vkConnect.getVkApiClient();
        this.groupActor = vkConnect.getGroupActor();
        this.vkInsertData = new VkInsertData(vkConnect);
        this.userActor = vkConnect.getUserActor();
    }

    @Override
    public void send(Integer integer, BoxAnswer boxAnswer) {
        throw new MailSendException();
    }

    @Override
    public void send(Integer contentId, Integer personId, BoxAnswer boxAnswer) {
        try {
            StringBuilder insertAnswer = new StringBuilder(vkInsertData.insertWords(boxAnswer.getMessage(), personId));
            if (boxAnswer.getKeyBoard() != null) {
                insertAnswer.append("\n\nМеню:\n");
                for (KeyBoardLine keyBoardLine : boxAnswer.getKeyBoard().getKeyBoardLines()) {
                    for (KeyBoardButton keyBoardButton : keyBoardLine.getKeyBoardButtons()) {
                        switch (keyBoardButton.getType()) {
                            case TEXT:
                                insertAnswer.append("- ").append(((KeyBoardButtonText) keyBoardButton).getLabel()).append("\n");
                        }

                    }
                }
            }

            vkApiClient.board().createComment(userActor, groupActor.getGroupId(), contentId)
                    .message(insertAnswer.toString()).fromGroup(true).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SendType getType() {
        return SendType.PUBLIC;
    }
}
