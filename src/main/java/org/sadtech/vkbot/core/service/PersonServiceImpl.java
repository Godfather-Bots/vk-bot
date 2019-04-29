package org.sadtech.vkbot.core.service;

import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserMin;
import org.apache.log4j.Logger;
import org.sadtech.bot.core.domain.Person;
import org.sadtech.bot.core.repository.PersonRepository;
import org.sadtech.bot.core.repository.impl.PersonRepositoryMap;
import org.sadtech.bot.core.service.PersonService;
import org.sadtech.vkbot.core.VkApi;
import org.sadtech.vkbot.core.VkConnect;

public class PersonServiceImpl implements PersonService {

    public static final Logger log = Logger.getLogger(PersonServiceImpl.class);

    private PersonRepository personRepository;
    private VkApi vkApi;

    public PersonServiceImpl(VkConnect vkConnect) {
        this.personRepository = new PersonRepositoryMap();
        vkApi = new VkApi(vkConnect);
    }

    public PersonServiceImpl(PersonRepository personRepository, VkConnect vkConnect) {
        this.personRepository = personRepository;
        vkApi = new VkApi(vkConnect);
    }

    public void add(User user) {
        Person person = new Person();
        person.setId(user.getId());
        person.setLastName(user.getLastName());
        person.setFirstName(user.getFirstName());
        personRepository.add(person);
        log.info("Пользователь добавлен в репозиторий");
    }

    @Override
    public void add(Person person) {
        personRepository.add(person);
        log.info("Пользователь добавлен в репозиторий");
    }

    public void add(UserMin userMin) {
        Person person = new Person();
        person.setId(userMin.getId());
        person.setFirstName(userMin.getFirstName());
        person.setLastName(userMin.getLastName());
        personRepository.add(person);
    }

    @Override
    public Person get(Integer id) {
        return personRepository.get(id);
    }

    @Override
    public boolean checkPerson(Integer idPerson) {
        log.info("Проверка наличия пользователя в репозитории");
        return get(idPerson) != null;
    }

    @Override
    public Person createPerson(Integer userId) {
        Person person = new Person();
        UserMin userMin = vkApi.getUserMini(userId);
        person.setId(userMin.getId());
        person.setLastName(userMin.getLastName());
        person.setFirstName(userMin.getFirstName());
        return person;
    }

}
