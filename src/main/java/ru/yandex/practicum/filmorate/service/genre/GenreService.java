package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface GenreService {
    GenreDTO getGenreById(Integer id);

    List<GenreDTO> getAllGenres();

    List<GenreDTO> getGenreByFilmId(Long id);

    void addGenreToFilm(Film film);

    void deleteGenreFromFilm(Film film);
}
