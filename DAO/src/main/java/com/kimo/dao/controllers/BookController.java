package com.kimo.dao.controllers;

import com.kimo.dao.domain.dto.AuthorDto;
import com.kimo.dao.domain.dto.BookDto;
import com.kimo.dao.domain.entities.BookEntity;
import com.kimo.dao.mappers.Mapper;
import com.kimo.dao.services.BookService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log
public class BookController {
    BookService bookService;
    Mapper<BookEntity,BookDto> bookMapper;
    public BookController(BookService bookService,Mapper<BookEntity,BookDto>bookMapper){
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        bookDto.setIsbn(isbn);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.addBook(isbn,bookEntity);
        BookDto result = bookMapper.mapTo(savedBook);
        if(bookExists) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }
    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable){
        Page<BookEntity> bookEntities = bookService.findAll(pageable);
        return bookEntities.map(bookMapper::mapTo);
    }
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> retrieveBook(@PathVariable("isbn") String isbn){
        Optional<BookEntity> bookEntity = bookService.findBook(isbn);
        return bookEntity.map(book -> {
            BookDto bookDto = bookMapper.mapTo(book);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn")String isbn,
                                                         @RequestBody BookDto bookDto)
    {
        if(!bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            BookEntity bookEntity = bookMapper.mapFrom(bookDto);
            BookEntity updatedBook = bookService.partialUpdateBook(isbn, bookEntity);
            return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
        }
    }
    @DeleteMapping("/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn)
    {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
