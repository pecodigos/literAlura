package com.pecodigos.literalura.dtos.mappers;

import com.pecodigos.literalura.dtos.BookDTO;
import com.pecodigos.literalura.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDTO(Book book);
}
