package com.kimo.dao.repository;

import com.kimo.dao.TestDataUtil;
import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.kimo.dao.TestDataUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith( SpringExtension.class)
public class BookEntityRepositoryIntegrationTests {
    @Autowired
    private BookRepository underTest;
    @Test
    public void testCreateAndRecallBook() {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        BookEntity bookEntity = createBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(bookEntity);

    }
    @Test
    public void testCreateAndRecallManyBooks(){
        AuthorEntity authorEntityA = TestDataUtil.createAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createAuthorC();
        BookEntity bookEntityA = createBookA(authorEntityA);
        BookEntity bookEntityB = createBookB(authorEntityB);
        BookEntity bookEntityC = createBookC(authorEntityC);
        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);
        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }
    @Test
    public void testCreateAndUpdateBook(){
        AuthorEntity authorEntity = createAuthorA();
        BookEntity bookEntity = createBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result.get()).isEqualTo(bookEntity);
        bookEntity.setTitle("Updated");
        underTest.save(bookEntity);
        result = underTest.findById(bookEntity.getIsbn());
        assertThat(result.get()).isEqualTo(bookEntity);
    }
    @Test
    public void testCreateAndDeleteBook(){
        AuthorEntity authorEntity = createAuthorA();
        BookEntity bookEntity = createBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        underTest.delete(bookEntity);
        result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();
    }

}
