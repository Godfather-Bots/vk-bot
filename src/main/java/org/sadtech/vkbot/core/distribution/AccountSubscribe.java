package org.sadtech.vkbot.core.distribution;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachmentType;
import org.sadtech.bot.core.domain.BoxAnswer;
import org.sadtech.bot.core.exception.PaymentException;
import org.sadtech.bot.core.service.AccountService;
import org.sadtech.bot.core.service.sender.Sent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountSubscribe extends AbstractBasketSubscribe<Message, Message> {

    private static final Logger log = LoggerFactory.getLogger(AccountSubscribe.class);

    private final AccountService accountService;
    private final Sent sent;
    private BoxAnswer answerSuccessfulPayment;

    public AccountSubscribe(AccountService accountService, Sent sent) {
        this.accountService = accountService;
        this.sent = sent;
    }

    @Override
    protected boolean check(Message userMessage) {
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
                    sent.send(message.getPeerId(), answerSuccessfulPayment);
                }
            } catch (PaymentException e) {
                log.error(e.getMessage());
                sent.send(message.getPeerId(), BoxAnswer.builder().message(e.getDescription()).build());
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
