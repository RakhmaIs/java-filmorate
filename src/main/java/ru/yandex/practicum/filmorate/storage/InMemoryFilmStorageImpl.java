package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorageImpl implements FilmStorage {
    private final Map<Long, Film> filmsMap = new HashMap<>();
    private Long idGen = 1L;

    @Override
    public FilmDto createFilm(Film film) {
        film.setId(idGen);
        log.info("Фильм " + film + " успешно добавлен");
        filmsMap.put(idGen++, film);
        return FilmMapper.fromFilmToFilmDto(film);
    }

    @Override
    public FilmDto updateFilm(Film film) {
        if (filmsMap.containsKey(film.getId())) {
            filmsMap.put(film.getId(), film);
            log.info("Фильм " + film + " успешно обновлен");
            return FilmMapper.fromFilmToFilmDto(film);
        }

        log.warn("Ошибка обновления фильма");
        throw new FilmNotFoundException("Фильма с id = " + film.getId() + " не существует");
    }


    @Override
    public List<FilmDto> readAllFilms() {
        log.info("Получен список фильмов");
        return FilmMapper.fromFilmToFilmDto(filmsMap.values());
    }

    @Override
    public Film getFilm(Long id) {
        if (filmsMap.containsKey(id)) {
            return filmsMap.get(id);
        }
        throw new FilmNotFoundException("Нет фильма c id = " + id);
    }
}
