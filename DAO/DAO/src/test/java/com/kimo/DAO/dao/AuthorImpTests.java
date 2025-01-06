package com.kimo.DAO.dao;

import com.kimo.DAO.dao.impl.AuthorDaoImp;
import com.kimo.DAO.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static com.kimo.DAO.TestDataUtil.createAuthorA;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorImpTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImp underTest;

    @Test
    public void testThatCreateAuthorGenerateCorrectSql(){
        Author author = createAuthorA();
        underTest.create(author);
        verify(jdbcTemplate).update(eq("INSERT INTO authors(id,name,age) VALUES(?,?,?)"),eq(1L),eq("Arthur"),eq(70)
        );
    }

    @Test
    public void testThatFindOneAuthorGenerateCorrectSql(){
        underTest.find(1L);
        verify(jdbcTemplate).query(eq("SELECT * from authors WHERE id = ? LIMIT 1"), ArgumentMatchers.<AuthorDaoImp.AuthorRowMapper>any(),eq(1L));
    }

    @Test
    public void testThatFindManyAuthorsGenerateCorrectSql(){
        underTest.findMany();
        verify(jdbcTemplate).query(eq("SELECT * from authors"),ArgumentMatchers.<AuthorDaoImp.AuthorRowMapper>any());
    }

    @Test
    public void testThatUpdateAuthorGenerateCorrectSql(){
        Author author = createAuthorA();
        underTest.update(author.getId(),author);
        verify(jdbcTemplate).update
                (eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?")
                        ,eq(1L),eq("Arthur"),eq(70),eq(1L));

    }

    @Test
    public void testThatDeleteAuthorGenerateCorrectSql(){
        Author author = createAuthorA();
        underTest.delete(1L);
        verify(jdbcTemplate).update(eq("DELETE from authors WHERE id = ? "),eq(1L)
                );
    }

}
