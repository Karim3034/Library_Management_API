package com.kimo.dao.services.impl;

import com.kimo.dao.domain.dto.BookDto;
import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.domain.entities.BookEntity;
import com.kimo.dao.repository.BookRepository;
import com.kimo.dao.services.AuthorService;
import com.kimo.dao.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public BookEntity addBook(String isbn,BookEntity book){
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
    @Override
    public List<BookEntity> retrieveBooks(){
        return StreamSupport.stream(bookRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
    @Override
    public Optional<BookEntity> findBook(String isbn){
       return bookRepository.findById(isbn);
    }
    @Override
    public BookEntity updateBook(String isbn, BookEntity bookEntity){
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }
    @Override
    public boolean isExists(String isbn){
        return bookRepository.existsById(isbn);
    }
    @Override
    public BookEntity partialUpdateBook(String isbn, BookEntity bookEntity){
        return bookRepository.findById(isbn).map(book -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(book::setTitle);
            return bookRepository.save(book);
        }).orElseThrow(()-> new RuntimeException("Book is not found :)")
        );
    }
    @Override
    public void delete(String isbn){
        bookRepository.deleteById(isbn);
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
       return bookRepository.findAll(pageable);
    }
}
