package com.pecodigos.literalura.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Author> authors;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> languages;

    private Integer downloadCount;
}
