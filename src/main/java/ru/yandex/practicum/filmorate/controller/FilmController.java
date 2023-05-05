package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDto>> readAllFilms() {
        return new ResponseEntity<>(filmService.readAllFilms(), HttpStatus.valueOf(200));
    }

    @PutMapping
    public ResponseEntity<FilmDto> updateFilm(@RequestBody Film film) {
        FilmValidator.validateFilm(film);
        FilmDto filmDto = filmService.updateFilm(film);
        return new ResponseEntity<>(filmDto, HttpStatus.valueOf(200));
    }


    @PostMapping
    public ResponseEntity<FilmDto> createFilm(@RequestBody Film film) {
        FilmValidator.validateFilm(film);
        return new ResponseEntity<>(filmService.createFilm(film), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<FilmDto> addLikeToFilm(@PathVariable(name = "id") @PositiveOrZero Long filmId,
                                                 @PathVariable @PositiveOrZero
                                                 Long userId) {

        return new ResponseEntity<>(filmService.addLikeToFilm(filmId, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<FilmDto> removeLike(@PathVariable(name = "id") Long filmId,
                                              @PathVariable Long userId) {
        // удаление лайка
        return new ResponseEntity<>(filmService.deleteLike(filmId, userId), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<FilmDto>> findPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        return new ResponseEntity<>(filmService.findPopularFilms(count), HttpStatus.OK);


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }
}
