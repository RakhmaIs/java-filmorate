package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    Genre getGenreById(Integer id);

    List<Genre> getAllGenres();

    void addGenreToFilm(Film film);

    void deleteGenreFromFilm(Film film);

    List<Genre> getGenreByFilmId(Long id);
}
