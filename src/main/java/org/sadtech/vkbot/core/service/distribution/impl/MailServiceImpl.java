package org.sadtech.vkbot.core.service.distribution.impl;

import org.apache.log4j.Logger;
import org.sadtech.bot.core.domain.Mail;
import org.sadtech.bot.core.repository.MailRepository;
import org.sadtech.bot.core.repository.impl.MailRepositoryList;
import org.sadtech.vkbot.core.service.distribution.MailService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MailServiceImpl implements MailService {

    private static final Logger log = Logger.getLogger(MailServiceImpl.class);

    private final MailRepository mailRepository;

    public MailServiceImpl() {
        this.mailRepository = new MailRepositoryList();
    }

    public MailServiceImpl(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    @Override
    public void add(Mail mail) {
        mailRepository.add(mail);
        log.info("Сообщение добавлено в репозиторий");
        log.info(mail);
    }

    @Override
    public List<Mail> getFirstEventByTime(LocalDateTime timeFrom, LocalDateTime timeTo) {
        log.info("Запрошены сообщения " + timeFrom + " - " + timeTo);
        List<Mail> mails = mailRepository.getMailByTime(timeFrom, timeTo);
        Set<Integer> people = new HashSet<>();
        List<Mail> returnMails = new ArrayList<>();
        for (int i = mails.size() - 1; i >= 0; i--) {
            if (!people.contains(mails.get(i).getPersonId())) {
                returnMails.add(mails.get(i));
                people.add(mails.get(i).getPersonId());
            }
        }
        return returnMails;
    }

    @Override
    public List<Mail> getLastEventByTime(LocalDateTime timeFrom, LocalDateTime timeTo) {
        List<Mail> mails = mailRepository.getMailByTime(timeFrom, timeTo);
        Set<Integer> people = new HashSet<>();
        List<Mail> returnMails = new ArrayList<>();
        for (Mail mail : mails) {
            if (!people.contains(mail.getPersonId())) {
                returnMails.add(mail);
                people.add(mail.getPersonId());
            }
        }
        return returnMails;
    }

    @Override
    public List<Mail> getEvent(LocalDateTime timeFrom, LocalDateTime timeTo) {
        log.info("Запрос на получение сообщений в интервале от " + timeFrom + " до " + timeTo);
        return mailRepository.getMailByTime(timeFrom, timeTo);
    }

}
