package com.kimo.dao.services;

import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity addAuthor(AuthorEntity author);
    List<AuthorEntity> retrieveAuthors();
    Optional<AuthorEntity> retrieveAuthor(Long id);
    AuthorEntity updateAuthor(Long id,AuthorEntity authorEntity);
    boolean isExists(Long id);
    AuthorEntity partialUpdate(Long id,AuthorEntity authorEntity);
    void delete(Long id);
}
