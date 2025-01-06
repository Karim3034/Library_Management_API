package com.kimo.dao.repository;

import com.kimo.dao.TestDataUtil;
import com.kimo.dao.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorEntityRepositoryIntegrationTests {


    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest){
        this.underTest = underTest;
    }
    @Test
    public void testCreateAndRecallAuthor() {
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(authorEntity);
    }
    @Test
    public void testCreateAndRecallManyAuthors(){
        AuthorEntity authorEntityA = TestDataUtil.createAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createAuthorC();
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);
        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }
    @Test
    public void testThatAuthorCanBeUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        underTest.save(authorEntity);
        authorEntity.setAge(71);
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }
    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createAuthorA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result= underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        underTest.delete(authorEntity);
        result= underTest.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }
    @Test
    public void testThatGetAuthorWithAgeLessThan(){
        AuthorEntity authorEntity1 = TestDataUtil.createAuthorA();
        underTest.save(authorEntity1);
        AuthorEntity authorEntity2 = TestDataUtil.createAuthorB();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.createAuthorC();
        underTest.save(authorEntity3);
        Iterable<AuthorEntity> result = underTest.ageLessThan(40);
        assertThat(result).containsExactly(authorEntity2, authorEntity3);
    }
    @Test
    public void testThatGetAuthorWithAgeGreaterThan(){
        AuthorEntity authorEntity1 = TestDataUtil.createAuthorA();
        underTest.save(authorEntity1);
        AuthorEntity authorEntity2 = TestDataUtil.createAuthorB();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.createAuthorC();
        underTest.save(authorEntity3);
        //Iterable<AuthorEntity> result = underTest.authorWithAgeGreaterThan(40);
        //assertThat(result).containsExactly(authorEntity1);
    }
}
