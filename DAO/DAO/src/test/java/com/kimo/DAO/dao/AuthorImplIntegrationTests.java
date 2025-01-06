package com.kimo.DAO.dao;

import com.kimo.DAO.TestDataUtil;
import com.kimo.DAO.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorImplIntegrationTests {

    @Autowired
    private AuthorDao underTest;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateAndRecallAuthor() {
        Author author = TestDataUtil.createAuthorA();

        underTest.create(author);

        Optional<Author> result = underTest.find(author.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(author);
    }
    @Test
    public void testCreateAndRecallManyAuthors(){
        Author authorA = TestDataUtil.createAuthorA();
        Author authorB = TestDataUtil.createAuthorB();
        Author authorC = TestDataUtil.createAuthorC();
        underTest.create(authorA);
        underTest.create(authorB);
        underTest.create(authorC);
        List<Author> result = underTest.findMany();
        assertThat(result).hasSize(3).containsExactly(authorA,authorB,authorC);
    }
    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createAuthorA();
        underTest.create(author);
        author.setAge(71);
        underTest.update(1L,author);
        Optional<Author> result = underTest.find(1L);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }
    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createAuthorA();
        underTest.create(author);
        Optional<Author> result= underTest.find(author.getId());
        assertThat(result).isPresent();
        underTest.delete(author.getId());
        result= underTest.find(author.getId());
        assertThat(result).isEmpty();
    }
}
