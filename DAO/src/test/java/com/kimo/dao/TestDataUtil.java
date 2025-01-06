package com.kimo.dao;

import com.kimo.dao.domain.entities.AuthorEntity;
import com.kimo.dao.domain.entities.BookEntity;


public final class TestDataUtil {
    private TestDataUtil() {}
    public static AuthorEntity createAuthorA() {
        return AuthorEntity.builder()
                .name("Arthur")
                .age(70)
                .build();
    }
    public static AuthorEntity createAuthorB() {
        return AuthorEntity.builder()
                .name("Karim")
                .age(21)
                .build();
    }
    public static AuthorEntity createAuthorC() {
        return AuthorEntity.builder()
                .name("Khalid")
                .age(30)
                .build();
    }

    public static BookEntity createBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("123")
                .title("The Hobbit")
                .authorEntity(authorEntity)
                .build();
    }public static BookEntity createBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("124")
                .title("Rich Dad and Boor Dad")
                .authorEntity(authorEntity)
                .build();
    }public static BookEntity createBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("125")
                .title("مميز بالاصفر")
                .authorEntity(authorEntity)
                .build();
    }
}
