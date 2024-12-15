package com.pecodigos.literalura.repositories;

import com.pecodigos.literalura.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b JOIN b.languages l WHERE l = :language")
    List<Book> findByLanguage(String language);
}
