package com.github.pavelvashkevich.repositories;

import com.github.pavelvashkevich.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByNameStartingWith(String pattern);

}
