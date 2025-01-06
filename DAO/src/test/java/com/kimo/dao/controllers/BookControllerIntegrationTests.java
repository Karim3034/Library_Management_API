package com.kimo.dao.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimo.dao.TestDataUtil;
import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.domain.entities.BookEntity;
import com.kimo.dao.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;
    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc,ObjectMapper objectMapper,BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }
    @Test
    public void testThatCreateBookSuccessfullyReturned201Created() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        String content = objectMapper.writeValueAsString(bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookEntity.getIsbn()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(content)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateBookSuccessfullySavedBook() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        BookEntity bookEntity = TestDataUtil.createBookA(authorEntity);
        String content = objectMapper.writeValueAsString(bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookEntity.getIsbn()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(content)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Hobbit")
        );
    }
    @Test
    public void testThatAllBooksRetrieved() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books").
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListBooksRetrievedBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookService.addBook(bookEntity.getIsbn(),bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books").
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("123")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("The Hobbit")
         );
    }
    @Test
    public void testThatBookFindByIsbnReturned200Ok() throws Exception{
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookService.addBook(bookEntity.getIsbn(),bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/123").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatBookFindByIsbnReturned404NotFound() throws Exception{
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookService.addBook(bookEntity.getIsbn(),bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatBookFindByIsbnReturnedBook() throws Exception{
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookService.addBook(bookEntity.getIsbn(),bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/123").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("123")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Hobbit")
        );
    }
    @Test
    public void testThatBookUpdateReturns200IsOK() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookEntity.setTitle(bookEntity.getTitle() + " Updated");
        BookEntity updatedBook = bookService.updateBook(bookEntity.getIsbn(),bookEntity);
        String result = objectMapper.writeValueAsString(updatedBook);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(result)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatBookUpdateReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookEntity.setTitle(bookEntity.getTitle() + " Updated");
        BookEntity updatedBook = bookService.updateBook(bookEntity.getIsbn(),bookEntity);
        String result = objectMapper.writeValueAsString(updatedBook);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(result)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("123")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Hobbit Updated")
        );
    }
    @Test
    public void testThatBookPartialUpdateReturns200IsOk() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookEntity.setTitle(bookEntity.getTitle() + " Updated");
        bookService.addBook(bookEntity.getIsbn(),bookEntity);
        BookEntity updatedBook = bookService.partialUpdateBook(bookEntity.getIsbn(),bookEntity);
        String result = objectMapper.writeValueAsString(updatedBook);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(result)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatBookPartialUpdateReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        bookEntity.setTitle(bookEntity.getTitle() + " Updated");
        bookService.addBook(bookEntity.getIsbn(),bookEntity);
        BookEntity updatedBook = bookService.partialUpdateBook(bookEntity.getIsbn(),bookEntity);
        String result = objectMapper.writeValueAsString(updatedBook);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(result)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntity.getTitle())
        );
    }
    @Test
    public void testThatBookDeleteAuthorReturn204NoContent() throws Exception {
        BookEntity bookEntity = TestDataUtil.createBookA(null);
        BookEntity savedBook = bookService.addBook(bookEntity.getIsbn(),bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + savedBook.getIsbn()).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
