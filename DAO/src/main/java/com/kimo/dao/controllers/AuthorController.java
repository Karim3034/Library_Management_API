package com.kimo.dao.controllers;

import com.kimo.dao.domain.dto.AuthorDto;
import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.mappers.Mapper;
import com.kimo.dao.repository.AuthorRepository;
import com.kimo.dao.services.AuthorService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;
    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper, AuthorRepository authorRepository){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
         AuthorEntity authorEntity = authorMapper.mapFrom(author);
         AuthorEntity savedAuthorEntity = authorService.addAuthor(authorEntity);
         log.info(authorEntity.toString());
         return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity),HttpStatus.CREATED);
    }
    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authorEntities = authorService.retrieveAuthors();
        return authorEntities.stream().
                map(authorMapper::mapTo).
                collect(Collectors.toList());
    }
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        AuthorEntity foundAuthor = authorService.retrieveAuthor(id).get();
        return new ResponseEntity<>(authorMapper.mapTo(foundAuthor),HttpStatus.OK);
//        Optional<AuthorEntity> author = authorService.retrieveAuthor(id);
//        return author.map(authorEntity -> {
//            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
//            return new ResponseEntity<>(authorDto,HttpStatus.OK);
//        }).orElse(
//                new ResponseEntity<>(HttpStatus.NOT_FOUND)
//        );
    }
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable(value = "id") Long id,
                                                  @RequestBody AuthorDto authorDto){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        if(!(authorService.isExists(id))){
            log.info("Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity author = authorService.updateAuthor(id,authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(author), HttpStatus.OK);
    }
    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id")Long id,
                                                         @RequestBody AuthorDto authorDto)
    {
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
            AuthorEntity author = authorService.partialUpdate(id,authorEntity);
            return new ResponseEntity<>(authorMapper.mapTo(author),HttpStatus.OK);
        }
    }
    @DeleteMapping("authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id")Long id){
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
