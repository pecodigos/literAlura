package com.pecodigos.literalura.services;

import com.pecodigos.literalura.dtos.AuthorDTO;
import com.pecodigos.literalura.dtos.BookDTO;
import com.pecodigos.literalura.dtos.mappers.AuthorMapper;
import com.pecodigos.literalura.dtos.mappers.BookMapper;
import com.pecodigos.literalura.entities.Author;
import com.pecodigos.literalura.entities.Book;
import com.pecodigos.literalura.exceptions.DataNotFoundException;
import com.pecodigos.literalura.repositories.AuthorRepository;
import com.pecodigos.literalura.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private BookFetcherService bookFetcherService;

    private BookRepository bookRepository;
    private BookMapper bookMapper;

    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;

    public BookDTO saveBookInfo(String bookTitle) {
        var optionalBook = bookRepository.findByTitle(bookTitle);

        if (optionalBook.isPresent()) {
            return bookMapper.toDTO(optionalBook.get());
        }

        var books = bookFetcherService.getBooks(bookTitle);

        if (books.isEmpty()) {
           throw new DataNotFoundException("No book found with that name");
        }

        var bookDTO = books.get(0);
        var authorDTOs = bookDTO.authors();

        var authors = authorDTOs.stream()
                .map(authorDTO ->
                        authorRepository.findByName(authorDTO.name())
                                .orElseGet(() -> {
                                    var newAuthor = Author.builder()
                                            .name(authorDTO.name())
                                            .birthYear(authorDTO.birthYear())
                                            .deathYear(authorDTO.deathYear())
                                            .build();
                                    return authorRepository.save(newAuthor);
                                })).toList();

        var book = Book.builder()
                .title(bookDTO.title())
                .languages(bookDTO.languages())
                .downloadCount(bookDTO.downloadCount())
                .authors(authors)
                .build();

        bookRepository.save(book);

        return bookMapper.toDTO(book);
    }

    public BookDTO getBookInfo(String bookTitle) {
        var repoBook = bookRepository.findByTitle(bookTitle);

        if (repoBook.isEmpty() || repoBook.get().getId() == null) {
            return saveBookInfo(bookTitle);
        }

        return bookMapper.toDTO(repoBook.get());
    }

    public List<BookDTO> listBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public List<BookDTO> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language)
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public List<AuthorDTO> listAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    public List<AuthorDTO> listLivingAuthors(Integer year) {
        var authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            throw new DataNotFoundException("There's no saved authors.");
        }

        return authors.stream()
                .filter(author -> author.getBirthYear() != null && (author.getDeathYear() == null || year > author.getBirthYear() && year < author.getDeathYear()))
                .map(authorMapper::toDTO)
                .toList();
    }
}
