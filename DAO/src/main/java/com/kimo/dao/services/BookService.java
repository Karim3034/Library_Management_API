package com.kimo.dao.services;

import com.kimo.dao.domain.dto.BookDto;
import com.kimo.dao.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Page<BookEntity> findAll(Pageable pageable);
    BookEntity addBook(String isbn,BookEntity book);
    List<BookEntity> retrieveBooks();
    Optional<BookEntity> findBook(String isbn);
    BookEntity updateBook(String isbn, BookEntity bookEntity);
    boolean isExists(String isbn);
    BookEntity partialUpdateBook(String isbn, BookEntity bookEntity);
    void delete(String isbn);
}
