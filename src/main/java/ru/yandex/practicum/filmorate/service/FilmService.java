package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;


import java.util.List;

public interface FilmService {
    FilmDto createFilm(Film film);

    FilmDto updateFilm(Film film);

    List<FilmDto> readAllFilms();

    FilmDto addLikeToFilm(Long filmId, Long userId);

    FilmDto getFilmById(Long id);

    FilmDto deleteLike(Long filmId, Long userId);

    List<FilmDto> findPopularFilms(Integer count);
}
