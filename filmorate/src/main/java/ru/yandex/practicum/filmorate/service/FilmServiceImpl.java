package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public FilmDto createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    @Override
    public FilmDto updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    @Override
    public List<FilmDto> readAllFilms() {
        return filmStorage.readAllFilms();
    }
}
