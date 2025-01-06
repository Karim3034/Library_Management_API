package com.kimo.DAO.dao.impl;

import com.kimo.DAO.dao.BookDao;
import com.kimo.DAO.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class BookDaoImp implements BookDao {
    private final JdbcTemplate jdbcTemplate;
    public BookDaoImp(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(Book book) {
        jdbcTemplate.update(
                "INSERT INTO books(isbn,title,author_id) VALUES(?,?,?)",
                book.getIsbn(),book.getTitle(),book.getAuthorId()
        );
    }

    public Optional<Book> find(String isbn) {
        List<Book> result= jdbcTemplate.query(("SELECT * from books WHERE isbn= ? LIMIT 1"),new booksRowMapper(),isbn);
        return result.stream().findFirst();
    }
    public static class booksRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }

    @Override
    public List<Book> findMany(){
        return jdbcTemplate.query("SELECT * FROM books",new booksRowMapper());
    }
    @Override
    public void update(String isbn,Book book){
        jdbcTemplate.update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(),book.getTitle(),book.getAuthorId(),isbn);
    }
    @Override
    public void delete(String number){
        jdbcTemplate.update("DELETE FROM books WHERE isbn = ?",number);
    }
}
