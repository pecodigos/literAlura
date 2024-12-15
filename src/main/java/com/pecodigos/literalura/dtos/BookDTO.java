package com.pecodigos.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(Long id, String title, List<AuthorDTO> authors, List<String> languages, @JsonProperty("download_count") Integer downloadCount) {

    @Override
    public String toString() {
        var authors = this.authors.stream()
                .map(AuthorDTO::name)
                .collect(Collectors.joining(", "));

        var languages = String.join(", ", this.languages);

        return String.format("""
                Title: %s.
                Author(s): %s.
                Language(s): %s.
                Download Count: %d.
                """, title, authors, languages, downloadCount);
    }
}
