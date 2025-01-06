package com.kimo.dao.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimo.dao.TestDataUtil;
import com.kimo.dao.domain.dto.AuthorDto;
import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.services.AuthorService;
import com.kimo.dao.services.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    public MockMvc mockMvc;
    public ObjectMapper objectMapper;
    public AuthorService authorService;
    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc,ObjectMapper objectMapper,AuthorService authorService){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturned201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        String content = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").
                contentType(MediaType.APPLICATION_JSON).
                content(content
                )).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateAuthorSuccessfullySavedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        String content = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").
                contentType(MediaType.APPLICATION_JSON).
                content(content
                )).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Arthur")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(70)
        );
    }
    @Test
    public void testThatAllAuthorsRetrieved() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors").
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatAllAuthorsRetrievedCorrectly() throws Exception {
        AuthorEntity authorEntity1 = TestDataUtil.createAuthorA();
        AuthorEntity authorEntity2 = TestDataUtil.createAuthorB();
        AuthorEntity authorEntity3 = TestDataUtil.createAuthorC();
        authorService.addAuthor(authorEntity1);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors").
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(authorEntity1.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(authorEntity1.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(authorEntity1.getAge())
        );
    }
    @Test
    public void testThatAuthorFindByIdReturned200OK() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1").
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatAuthorFindByIdReturned404NotFound() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors"+authorEntity.getId()+1).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatAuthorFindByIdReturnedAuthor() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1").
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Arthur")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(70)
        );
    }
    @Test
    public void testThatAuthorUpdateReturns200IsOK() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        authorEntity.setAge(90);
        authorService.updateAuthor(authorEntity.getId(),authorEntity);
        String updatedAuthor = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedAuthor)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatAuthorUpdateIfNotFoundReturns404IsNotFound() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        authorEntity.setAge(90);
        authorService.updateAuthor(authorEntity.getId(),authorEntity);
        String updatedAuthor = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/2").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedAuthor)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatAuthorUpdateReturnsAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        authorEntity.setAge(90);
        authorService.updateAuthor(authorEntity.getId(),authorEntity);
        String updatedAuthor = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedAuthor)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Arthur")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(90)
        );
    }
    @Test
    public void testThatAuthorPartialUpdateReturns200IfExist() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        authorEntity.setAge(90);
        authorService.partialUpdate(authorEntity.getId(),authorEntity);
        String updatedAuthor = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedAuthor)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatAuthorPartialUpdateReturns404IfNotExist() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        authorEntity.setAge(90);
        authorService.partialUpdate(authorEntity.getId(),authorEntity);
        String updatedAuthor = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/3").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedAuthor)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatAuthorPartialUpdateReturnsAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        authorService.addAuthor(authorEntity);
        authorEntity.setAge(90);
        authorService.partialUpdate(authorEntity.getId(),authorEntity);
        String updatedAuthor = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedAuthor)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Arthur")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(90)
        );
    }
    @Test
    public void testThatAuthorDeleteAuthorReturn204NoContent() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        AuthorEntity savedAuthor = authorService.addAuthor(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId()).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
