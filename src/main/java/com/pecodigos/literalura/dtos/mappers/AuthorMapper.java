package com.pecodigos.literalura.dtos.mappers;

import com.pecodigos.literalura.dtos.AuthorDTO;
import com.pecodigos.literalura.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDTO(Author author);
}
