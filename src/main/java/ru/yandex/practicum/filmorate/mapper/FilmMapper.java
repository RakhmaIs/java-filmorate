package ru.yandex.practicum.filmorate.mapper;


import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilmMapper {
    private FilmMapper() {
    }

    public static FilmDTO fromFilmToFilmDto(Film film) {
        if (film == null) {
            return null;
        }
        return FilmDTO.builder()
                .id(film.getId())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .name(film.getName())
                .likesIds(film.getLikesIds())
                .mpa(film.getMpa())
                .genres(film.getGenres())
                .build();
    }

    public static Film fromFilmDTOFilm(FilmDTO filmDto) {
        if (filmDto == null) {
            return null;
        }
        return Film.builder()
                .id(filmDto.getId())
                .description(filmDto.getDescription())
                .releaseDate(filmDto.getReleaseDate())
                .duration(filmDto.getDuration())
                .name(filmDto.getName())
                .mpa(filmDto.getMpa())
                .genres(filmDto.getGenres())
                .build();
    }

    public static List<FilmDTO> fromFilmListToFilmDtoList(Collection<Film> films) {
        List<FilmDTO> filmDto = new ArrayList<>();
        for (Film film : films) {
            filmDto.add(fromFilmToFilmDto(film));
        }
        return filmDto;
    }
}
