package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;

import java.util.List;

public interface FilmStorage  {
    FilmDto createFilm(Film film);
    FilmDto updateFilm(Film film);
    List<FilmDto> readAllFilms();
}
