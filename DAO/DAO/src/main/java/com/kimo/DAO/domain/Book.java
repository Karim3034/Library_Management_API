package com.kimo.DAO.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Book {
    private String isbn;
    private String title;
    private long authorId;
}
