package ru.yandex.practicum.filmorate.mapper;


import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilmMapper {
    private FilmMapper() {

    }

    public static FilmDto fromFilmToFilmDto(Film film) {
        if (film == null) {
            return null;
        }
        return FilmDto.builder()
                .id(film.getId())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .name(film.getName())
                .build();
    }

    public static Film fromFilmToFilmDto(FilmDto filmDto) {
        if (filmDto == null) {
            return null;
        }
        return Film.builder()
                .id(filmDto.getId())
                .description(filmDto.getDescription())
                .releaseDate(filmDto.getReleaseDate())
                .duration(filmDto.getDuration())
                .name(filmDto.getName())
                .build();
    }

    public static List<FilmDto> fromFilmToFilmDto(Collection<Film> films) {
        List<FilmDto> filmDto = new ArrayList<>();
        for (Film film : films) {
            filmDto.add(fromFilmToFilmDto(film));
        }
        return filmDto;
    }
}
