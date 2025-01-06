package com.kimo.DAO.dao;

import com.kimo.DAO.TestDataUtil;
import com.kimo.DAO.dao.impl.BookDaoImp;
import com.kimo.DAO.domain.Author;
import com.kimo.DAO.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.kimo.DAO.TestDataUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith( SpringExtension.class)
public class BookImplIntegrationTests {

    private AuthorDao authorDao;
    private BookDaoImp underTest;
    @Autowired
    public BookImplIntegrationTests(AuthorDao authorDao, BookDaoImp underTest){
        this.authorDao = authorDao;
        this.underTest = underTest;
    }
    @Test
    public void testCreateAndRecallBook() {
        Author author = TestDataUtil.createAuthorA();
        authorDao.create(author);
        Book book = createBookA();
        underTest.create(book);
        Optional<Book> result = underTest.find(book.getIsbn());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(book);

    }
    @Test
    public void testCreateAndRecallManyBooks(){
        Author authorA = TestDataUtil.createAuthorA();
        Author authorB = TestDataUtil.createAuthorB();
        Author authorC = TestDataUtil.createAuthorC();
        authorDao.create(authorA);
        authorDao.create(authorB);
        authorDao.create(authorC);
        Book bookA = createBookA();
        Book bookB = createBookB();
        Book bookC = createBookC();
        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);
        List<Book> result = underTest.findMany();
        assertThat(result).hasSize(3).containsExactly(bookA,bookB,bookC);
    }
    @Test
    public void testCreateAndUpdateBook(){
        Author author = createAuthorA();
        authorDao.create(author);
        Book book = createBookA();
        underTest.create(book);
        Optional<Book> result = underTest.find(book.getIsbn());
        assertThat(result.get()).isEqualTo(book);
        book.setTitle("Updated");
        underTest.update(book.getIsbn(),book);
        result = underTest.find(book.getIsbn());
        assertThat(result.get()).isEqualTo(book);
    }
    @Test
    public void testCreateAndDeleteBook(){
        Author author = createAuthorA();
        authorDao.create(author);
        Book book = createBookA();
        underTest.create(book);
        Optional<Book> result = underTest.find(book.getIsbn());
        assertThat(result).isPresent();
        underTest.delete(book.getIsbn());
        result = underTest.find(book.getIsbn());
        assertThat(result).isEmpty();
    }
}
