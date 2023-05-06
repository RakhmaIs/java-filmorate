package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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

    @Override
    public FilmDto addLikeToFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.getLikesIds().add(userId);
        return FilmMapper.fromFilmToFilmDto(film);
    }

    @Override
    public FilmDto deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film.getLikesIds().contains(userId)) {
            film.getLikesIds().remove(userId);
            return FilmMapper.fromFilmToFilmDto(film);
        }
        throw new FilmNotFoundException("Нет такого фильма");
    }

    @Override
    public FilmDto getFilmById(Long id) {
        return FilmMapper.fromFilmToFilmDto(filmStorage.getFilm(id));
    }

    @Override
    public List<FilmDto> findPopularFilms(Integer count) {
        List<FilmDto> popFilm = filmStorage.readAllFilms();
        Comparator<FilmDto> filmComparator = (a, b) -> b.getLikesIds().size() - a.getLikesIds().size();
        popFilm.sort(filmComparator);
        return popFilm.stream().limit(count).collect(Collectors.toList());
    }
}

