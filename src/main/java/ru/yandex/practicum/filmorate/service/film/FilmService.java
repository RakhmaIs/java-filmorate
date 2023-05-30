package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    FilmDTO createFilm(Film film);

    FilmDTO updateFilm(Film film);

    List<FilmDTO> readAllFilms();

    FilmDTO getFilmById(Long id);

    void deleteLike(Long filmId, Long userId);

    List<FilmDTO> findPopularFilms(Integer count);

    void deleteFilmById(Long id);

    void addLikeToFilm(Long filmId, Long userId);

}