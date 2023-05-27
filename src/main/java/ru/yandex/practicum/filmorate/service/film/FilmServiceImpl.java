package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;


    @Autowired
    public FilmServiceImpl(@Qualifier("FilmDBStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;

    }

    @Override
    public void addLikeToFilm(Long filmId, Long userId) {
        filmStorage.addLikeToFilm(filmId, userId);
    }

    @Override
    public FilmDTO createFilm(Film film) {
        return FilmMapper.fromFilmToFilmDto(filmStorage.createFilm(film));
    }

    @Override
    public FilmDTO updateFilm(Film film) {
        return FilmMapper.fromFilmToFilmDto(filmStorage.updateFilm(film));
    }

    @Override
    public List<FilmDTO> readAllFilms() {
        return FilmMapper.fromFilmListToFilmDtoList(filmStorage.readAllFilms());
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        filmStorage.deleteLikeFromFilm(filmId, userId);
    }

    @Override
    public FilmDTO getFilmById(Long id) {
        return FilmMapper.fromFilmToFilmDto(filmStorage.getFilmById(id));
    }

    @Override
    public List<FilmDTO> findPopularFilms(Integer count) {
        return FilmMapper.fromFilmListToFilmDtoList(filmStorage.findPopularFilms(count));
    }

    @Override
    public void deleteFilmById(Long id) {
        filmStorage.deleteFilmById(id);
    }
}

