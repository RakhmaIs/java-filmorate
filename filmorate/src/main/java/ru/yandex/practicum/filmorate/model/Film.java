package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
@Builder
@Data
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;

}
