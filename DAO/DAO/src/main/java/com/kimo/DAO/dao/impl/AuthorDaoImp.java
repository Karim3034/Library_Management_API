package com.kimo.DAO.dao.impl;

import com.kimo.DAO.dao.AuthorDao;
import com.kimo.DAO.domain.Author;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSet.*;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImp implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;
    public AuthorDaoImp(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO authors(id,name,age) VALUES(?,?,?)",
                author.getId(),author.getName(),author.getAge());
    }
    @Override
    public Optional<Author> find(long id) {
        List<Author> result = jdbcTemplate.query("SELECT * from authors WHERE id = ? LIMIT 1", new AuthorRowMapper(), id);
        return result.stream().findFirst();
    }
    public static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
    @Override
    public List<Author> findMany(){
        return jdbcTemplate.query("SELECT * from authors",new AuthorRowMapper());
    }

    @Override
    public void update(Long id,Author author){
        jdbcTemplate.update("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                author.getId(),author.getName(),author.getAge(),id);
    }

    @Override
    public void delete(long id){
        jdbcTemplate.update("DELETE from authors WHERE id = ? ",id);
    }
}
