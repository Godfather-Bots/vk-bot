package org.sadtech.vk.bot.sdk;

import org.sadtech.autoresponder.repository.UnitPointerRepository;
import org.sadtech.social.bot.GeneralAutoResponder;
import org.sadtech.social.bot.domain.unit.MainUnit;
import org.sadtech.social.core.domain.content.Mail;
import org.sadtech.social.core.service.MessageService;
import org.sadtech.social.core.service.sender.Sending;

import java.util.Set;

public class MessageAutoresponderVk extends GeneralAutoResponder<Mail> {

    public MessageAutoresponderVk(Set<MainUnit> menuUnit, Sending sending, MessageService<Mail> messageService, UnitPointerRepository<MainUnit> unitPointerRepository) {
        super(menuUnit, sending, messageService, unitPointerRepository);
    }

}
