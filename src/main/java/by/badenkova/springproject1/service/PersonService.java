package by.badenkova.springproject1.service;

import by.badenkova.springproject1.entity.Person;

import java.util.Optional;

public interface PersonService {
    Iterable<Person> getAllPerson();
    void addNewPerson(Person person);
    void deletePerson(Person person );
    void editPerson(Person person);

    Optional<Person> getById(long id);
}
