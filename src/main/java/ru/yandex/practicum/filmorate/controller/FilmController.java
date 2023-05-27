package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> readAllFilms() {
        log.info("Получен запрос на получение списка всех фильмов.");
        return new ResponseEntity<>(filmService.readAllFilms(), HttpStatus.valueOf(200));
    }

    @PutMapping
    public ResponseEntity<FilmDTO> updateFilm(@RequestBody Film film) {
        log.info("Получен запрос на обновление фильма : {}", film);
        FilmValidator.validateFilm(film);
        FilmDTO filmDto = filmService.updateFilm(film);
        return new ResponseEntity<>(filmDto, HttpStatus.valueOf(200));
    }


    @PostMapping
    public ResponseEntity<FilmDTO> createFilm(@RequestBody Film film) {
        log.info("Получен запрос на добавление фильма : {}", film);
        FilmValidator.validateFilm(film);
        return new ResponseEntity<>(filmService.createFilm(film), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<FilmDTO> addLikeToFilm(@PathVariable(name = "id") @PositiveOrZero Long filmId,
                                                 @PathVariable @PositiveOrZero
                                                 Long userId) {
        log.info("Получен запрос на добавление лайка пользователя c id = {} к фильму c id {} ", userId, filmId);
        filmService.addLikeToFilm(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<FilmDTO> deleteLikeFromFilm(@PathVariable(name = "id") Long filmId,
                                                      @PathVariable Long userId) {
        log.info("Получен запрос на удаление лайка от пользователя с id = {} , от фильма c id =  {}", userId, filmId);
        filmService.deleteLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<FilmDTO>> findPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Получен запрос на получение самых популярных фильмов в количестве : {}", count);
        return new ResponseEntity<>(filmService.findPopularFilms(count), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Long id) {
        log.info("Получен запрос на получения фильма по id = {}", id);
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable Long id) {
        log.info("Получен запрос на удаление фильма по id = {}", id);
        filmService.deleteFilmById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
