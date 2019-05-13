package org.sadtech.vkbot.core.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.messages.Message;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.domain.Mail;
import org.sadtech.vkbot.core.service.distribution.MailService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MailSubscriber implements EventSubscribe<JsonObject> {

    private static final Logger log = Logger.getLogger(MailSubscriber.class);

    private final MailService mailService;
    private Set<Integer> admins = new HashSet<>();
    private final Map<String, EventSubscribe<Message>> eventDistributionMap = new HashMap<>();

    public MailSubscriber(MailService mailService) {
        this.mailService = mailService;
    }

    public void setAdmins(Set<Integer> admins) {
        this.admins = admins;
    }

    public Set<Integer> getAdmins() {
        return admins;
    }

    @Override
    public void update(JsonObject object) {
        log.info("Дистрибьютор получил событие - сообщение");
        Gson gson = new Gson();
        Message userMessage = gson.fromJson(object, Message.class);
        log.info(userMessage);

        if (userMessage.getPeerId() > 2000000000) {
            if (eventDistributionMap.containsKey("chat")) {
                eventDistributionMap.get("chat").update(userMessage);
            }
        } else {
            if (admins.contains(userMessage.getPeerId()) && eventDistributionMap.containsKey("terminal")) {
                log.info("Сообщение отправлено в репозиторий команд");
                eventDistributionMap.get("terminal").update(userMessage);
            } else {
                log.info("Сообщение отправленно на добавление в репозиторий");
                mailService.add(createMaail(userMessage));
            }
        }
    }

    private Mail createMaail(Message message) {
        Mail mail = new Mail();
        mail.setMessage(message.getText());
        mail.setDate(message.getDate());
        mail.setId(message.getId());
        mail.setPersonId(message.getPeerId());
        return mail;
    }

}
