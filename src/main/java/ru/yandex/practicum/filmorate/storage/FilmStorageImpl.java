package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FilmStorageImpl implements FilmStorage {
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
            log.info("Фильм " + film + " успешно обновлен");//залогировать позитивный
            return FilmMapper.fromFilmToFilmDto(film);
        }
        film.setId(0L);
        log.warn("Ошибка обновления фильма");// залогировать негативный сценарий
        return FilmMapper.fromFilmToFilmDto(film);
    }

    @Override
    public List<FilmDto> readAllFilms() {
        log.info("Получен список фильмов");
        return FilmMapper.fromFilmToFilmDto(filmsMap.values());
    }
}
