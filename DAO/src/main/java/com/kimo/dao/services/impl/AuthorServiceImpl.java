package com.kimo.dao.services.impl;

import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.repository.AuthorRepository;
import com.kimo.dao.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorEntity addAuthor(AuthorEntity author){
        return authorRepository.save(author);
    }
    @Override
    public List<AuthorEntity> retrieveAuthors(){
        return StreamSupport.stream(authorRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
    @Override
    public Optional<AuthorEntity> retrieveAuthor(Long id){
        return authorRepository.findById(id);
    }
    @Override
    public AuthorEntity updateAuthor(Long id,AuthorEntity authorEntity){
        return authorRepository.save(authorEntity);
    }
    @Override
    public boolean isExists(Long id){
        return authorRepository.existsById(id);
    }
    @Override
    public AuthorEntity partialUpdate(Long id,AuthorEntity authorEntity){
        authorEntity.setId(id);
        return authorRepository.findById(id).map(author -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(author::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(author::setAge);
            return authorRepository.save(author);
        }).orElseThrow(()-> new RuntimeException("Author Not Found :)")
        );
    }
    @Override
    public void delete(Long id){
        authorRepository.deleteById(id);
    }

}
