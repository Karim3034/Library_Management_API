package com.kimo.DAO.domain;

import lombok.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Author {
    private long id;

    private String  name;

    private int age;

}
