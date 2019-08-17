package org.sadtech.vkbot.core.distribution.subscriber;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachmentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sadtech.social.core.domain.BoxAnswer;
import org.sadtech.social.core.exception.PaymentException;
import org.sadtech.social.core.service.AccountService;
import org.sadtech.social.core.service.sender.Sending;

@Slf4j
@RequiredArgsConstructor
public class AccountSubscribe extends AbstractBasketSubscribe<Message, Message> {

    private final AccountService accountService;
    private final Sending sending;
    private BoxAnswer answerSuccessfulPayment;

    @Override
    public boolean check(Message userMessage) {
        return userMessage.getAttachments().size() > 0
                && MessageAttachmentType.LINK.equals(userMessage.getAttachments().get(0).getType())
                && "Payment awaiting acceptance".equals(userMessage.getAttachments().get(0).getLink().getCaption());
    }

    @Override
    public void processing(Message message) {
        if (message.getText() != null) {
            try {
                Integer valueSum = Integer.valueOf(message.getAttachments().get(0).getLink().getTitle().split(" ")[0]);
                if (accountService.pay(Integer.valueOf(message.getText()), message.getPeerId(), valueSum) && answerSuccessfulPayment != null) {
                    sending.send(message.getPeerId(), answerSuccessfulPayment);
                }
            } catch (PaymentException e) {
                log.error(e.getMessage());
                sending.send(message.getPeerId(), BoxAnswer.builder().message(e.getDescription()).build());
            }
        }
    }

    public BoxAnswer getAnswerSuccessfulPayment() {
        return answerSuccessfulPayment;
    }

    public void setAnswerSuccessfulPayment(BoxAnswer answerSuccessfulPayment) {
        this.answerSuccessfulPayment = answerSuccessfulPayment;
    }
}
