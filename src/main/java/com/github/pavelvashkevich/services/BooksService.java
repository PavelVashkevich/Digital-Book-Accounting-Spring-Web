package com.github.pavelvashkevich.services;

import com.github.pavelvashkevich.model.Book;
import com.github.pavelvashkevich.model.Person;
import com.github.pavelvashkevich.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private static final String YEAR_OF_PUBLISH_PROP_NAME = "yearOfPublish";

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAllSortByYearOfPublish() {
        return booksRepository.findAll(Sort.by(YEAR_OF_PUBLISH_PROP_NAME));
    }

    public List<Book> findAllSortByYearOfPublish(int page, int booksPerPage) {
        return booksRepository.findAll(
                PageRequest.of(page, booksPerPage, Sort.by(YEAR_OF_PUBLISH_PROP_NAME))).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    public List<Book> findByNameStartingWith(String pattern) {
        return booksRepository.findByNameStartingWith(pattern);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public Person findPatronInfo(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.map(Book::getPatron).orElse(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assignPatron(int id, Person patron) {
        Optional<Book> bookToAssignPatron = booksRepository.findById(id);
        bookToAssignPatron.ifPresent(book ->
        {
            book.setPatron(patron);
            book.setBorrowedTime(Calendar.getInstance());
        });
    }

    @Transactional
    public void releaseBook(int id) {
        Optional<Book> bookToRelease = booksRepository.findById(id);
        bookToRelease.ifPresent(book -> book.setPatron(null));
    }
}