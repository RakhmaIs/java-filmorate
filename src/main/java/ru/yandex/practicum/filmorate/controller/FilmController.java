package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmDto;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

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
        ResponseEntity<List<FilmDto>> response = new ResponseEntity<>(filmService.readAllFilms(), HttpStatus.valueOf(200));
        return response;
    }

    @PutMapping
    public ResponseEntity<FilmDto> updateFilm(@RequestBody Film film) {
        if (FilmValidator.validateFilm(film)) {
            ResponseEntity<FilmDto> response = new ResponseEntity<>(filmService.updateFilm(film), HttpStatus.valueOf(200));
            if (response.getBody().getId() == 0L) {
                return new ResponseEntity<>(FilmMapper.fromFilmToFilmDto(film), HttpStatus.valueOf(500));
            }
            return response;
        }
        return new ResponseEntity<>(FilmMapper.fromFilmToFilmDto(film), HttpStatus.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<FilmDto> createFilm(@RequestBody Film film) {
        if (FilmValidator.validateFilm(film)) {
            ResponseEntity<FilmDto> response = new ResponseEntity<>(filmService.createFilm(film), HttpStatus.valueOf(200));
            return response;
        }
        return new ResponseEntity<>(FilmMapper.fromFilmToFilmDto(film), HttpStatus.valueOf(500));
    }
}
