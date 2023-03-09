package com.github.pavelvashkevich.services;

import com.github.pavelvashkevich.model.Book;
import com.github.pavelvashkevich.model.Person;
import com.github.pavelvashkevich.repositories.PeopleRepository;
import com.github.pavelvashkevich.util.OverdueChecker;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final OverdueChecker overdueChecker;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, OverdueChecker overdueChecker) {
        this.peopleRepository = peopleRepository;
        this.overdueChecker = overdueChecker;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> findPersonBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();
            checkOverdueBooks(books);
            return books;
        }
        return Collections.emptyList();
    }

    private void checkOverdueBooks(List<Book> booksToCheck) {
        overdueChecker.checkBooksOverdue(booksToCheck);
    }
}
