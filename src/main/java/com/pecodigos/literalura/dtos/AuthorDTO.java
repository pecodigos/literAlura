package com.pecodigos.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorDTO(Long id,
                        String name,
                        @JsonProperty("birth_year") Integer birthYear,
                        @JsonProperty("death_year") Integer deathYear) {

    @Override
    public String toString() {
        return String.format("""
                Name: %s.
                Birth year: %d.
                Death Year: %d.
                """, this.name, this.birthYear, this.deathYear);
    }
}
