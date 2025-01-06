package com.kimo.DAO.dao;

import com.kimo.DAO.dao.impl.AuthorDaoImp;
import com.kimo.DAO.dao.impl.BookDaoImp;
import com.kimo.DAO.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.kimo.DAO.TestDataUtil.createBookA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookImpTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImp underTest;

    @Test
    public void testThatCreateBookGenerateCorrectSql(){
        Book book = createBookA();
        underTest.create(book);
        verify(jdbcTemplate).update(
                eq("INSERT INTO books(isbn,title,author_id) VALUES(?,?,?)"),
                eq("123"),eq("The Hobbit"),eq(1L));
    }
    @Test
    public void testThatFindOneBookGenerateCorrectSql(){
        underTest.find("123");
        verify(jdbcTemplate).query(eq("SELECT * from books WHERE isbn= ? LIMIT 1"), ArgumentMatchers.<BookDaoImp.booksRowMapper>any(), eq("123"));
    }
    @Test
    public void testThatFindManyBooksGenerateCorrectSql(){
        List<Book> result = underTest.findMany();
        verify(jdbcTemplate).query(eq("SELECT * FROM books"),
                ArgumentMatchers.<AuthorDaoImp.AuthorRowMapper>any());
    }
    @Test
    public void testThatBookCanBeUpdated(){
        Book book = createBookA();
        underTest.create(book);
        underTest.update(book.getIsbn(),book);
        verify(jdbcTemplate).update(eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq(book.getIsbn()),eq(book.getTitle()),eq(book.getAuthorId()),eq(book.getIsbn()));
    }
    @Test
    public void testThatBookCanBeDeleted(){
        underTest.delete("123");
        verify(jdbcTemplate).update(eq("DELETE FROM books WHERE isbn = ?"),eq("123"));
    }
}
