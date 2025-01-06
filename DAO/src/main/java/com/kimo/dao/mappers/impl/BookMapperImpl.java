package com.kimo.dao.mappers.impl;

import com.kimo.dao.domain.dto.BookDto;
import com.kimo.dao.domain.entities.BookEntity;
import com.kimo.dao.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    private ModelMapper modelMapper;
    public BookMapperImpl(ModelMapper modelMapper){this.modelMapper=modelMapper;}
    @Override
    public BookDto mapTo(BookEntity bookEntity){
       return modelMapper.map(bookEntity,BookDto.class);
    }
    @Override
    public BookEntity mapFrom(BookDto bookDto){
       return modelMapper.map(bookDto,BookEntity.class);
    }
}
