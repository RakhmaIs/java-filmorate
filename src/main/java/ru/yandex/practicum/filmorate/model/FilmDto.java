package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
public class FilmDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Set<Long> likesIds;

}
