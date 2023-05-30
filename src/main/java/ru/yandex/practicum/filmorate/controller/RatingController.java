package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.service.rating.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class RatingController {

    private final MpaService mpaService;

    @Autowired
    public RatingController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public ResponseEntity<List<MpaDTO>> findAllRatings() {
        log.info("Получен запрос на получение всех имеющихся рейтингов.");
        return new ResponseEntity<>(mpaService.getRating(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MpaDTO> findRatingById(@PathVariable Integer id) {
        log.info("Получен запрос на получение рейтинга по id = {}.", id);
        return new ResponseEntity<>(mpaService.getRatingById(id), HttpStatus.OK);
    }
}
