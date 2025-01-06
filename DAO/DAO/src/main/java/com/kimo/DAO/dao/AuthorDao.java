package com.kimo.DAO.dao;

import com.kimo.DAO.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);
    Optional<Author> find(long l);
    List<Author> findMany();
    void update(Long id,Author author);
    void delete(long id);
}
