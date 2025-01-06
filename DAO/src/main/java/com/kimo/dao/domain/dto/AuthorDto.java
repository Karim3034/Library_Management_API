package com.kimo.dao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private int age;
}
