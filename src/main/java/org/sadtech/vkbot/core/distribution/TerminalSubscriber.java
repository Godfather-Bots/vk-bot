package org.sadtech.vkbot.core.distribution;

import com.vk.api.sdk.objects.messages.Message;
import org.apache.log4j.Logger;
import org.sadtech.vkbot.core.service.distribution.MailService;

public class TerminalSubscriber implements EventSubscribe<Message> {

    public static final Logger log = Logger.getLogger(TerminalSubscriber.class);

    private MailService mailService;

    public TerminalSubscriber(MailSubscriber mailSubscriber, MailService mailService) {
        this.mailService = mailService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void update(Message object) {
        log.info("Сообщение отправленно на добавление в репозиторий команд");
    }

}
