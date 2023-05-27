package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> readAllFilms();

    Film getFilmById(Long id);

    void deleteFilmById(Long id);

    void deleteLikeFromFilm(Long filmId, Long userId);

    void addLikeToFilm(Long filmId, Long userId);

    boolean filmExists(Long filmId);

    List<Film> findPopularFilms(Integer count);
}
