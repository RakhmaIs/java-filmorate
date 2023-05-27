package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("{id}")
    public ResponseEntity<GenreDTO> findGenreById(@PathVariable Integer id) {
        log.info("Получен запрос на получения жанра по id = {}", id);
        return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> findAllGenre() {
        log.info("Получен запрос на получение всех имеющихся жанров.");
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }
}
