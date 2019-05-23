package org.sadtech.vkbot.core.distribution;

import com.vk.api.sdk.objects.messages.Message;
import org.sadtech.bot.core.service.AccountService;

public class AccountSubscribe extends AbstractBasketSubscribe<Message, Message> {

    private final AccountService accountService;

    public AccountSubscribe(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected boolean check(Message userMessage) {
        return userMessage.getAttachments().size() > 0 && "Денежный перевод".equals(userMessage.getAttachments().get(0).getLink().getCaption());
    }

    @Override
    public void processing(Message object) {
        accountService.pay(0);
    }
}
