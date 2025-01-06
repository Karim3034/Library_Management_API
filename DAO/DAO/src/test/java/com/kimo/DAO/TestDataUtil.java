package com.kimo.DAO;

import com.kimo.DAO.domain.Author;
import com.kimo.DAO.domain.Book;


public final class TestDataUtil {
    private TestDataUtil() {}
    public static Author createAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Arthur")
                .age(70)
                .build();
    }
    public static Author createAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Karim")
                .age(21)
                .build();
    }
    public static Author createAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Khalid")
                .age(30)
                .build();
    }

    public static Book createBookA() {
        return Book.builder()
                .isbn("123")
                .title("The Hobbit")
                .authorId(1L)
                .build();
    }public static Book createBookB() {
        return Book.builder()
                .isbn("124")
                .title("Rich Dad and Boor Dad")
                .authorId(2L)
                .build();
    }public static Book createBookC() {
        return Book.builder()
                .isbn("125")
                .title("مميز بالاصفر")
                .authorId(3L)
                .build();
    }
}
