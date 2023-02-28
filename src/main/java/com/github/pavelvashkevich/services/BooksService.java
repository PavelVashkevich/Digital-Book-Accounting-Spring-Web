package com.github.pavelvashkevich.services;

import com.github.pavelvashkevich.model.Book;
import com.github.pavelvashkevich.model.Person;
import com.github.pavelvashkevich.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
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
        bookToAssignPatron.ifPresent(book -> book.setPatron(patron));
    }

    @Transactional
    public void releaseBook(int id) {
        Optional<Book> bookToRelease = booksRepository.findById(id);
        bookToRelease.ifPresent(book -> book.setPatron(null));
    }
}