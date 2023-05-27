package ru.yandex.practicum.filmorate.validation;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    private FilmValidator() {}

    public static void validateFilm(Film film) {
        if (film == null || film.getDescription() == null || film.getReleaseDate() == null || film.getName() == null || film.getDuration() == null) {
            throw new ValidationException(HttpStatus.valueOf(500), "Поле не может быть null");
        }
        if (film.getName().isBlank())
            throw new ValidationException(HttpStatus.valueOf(500), "Имя не может быть пустым.");
        if (film.getDescription().length() > 200)
            throw new ValidationException(HttpStatus.valueOf(500), "Описание не может быть больше 200 - сот символов");
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException(HttpStatus.valueOf(500), "Дата релиза не может быть раньше 28.12.1895");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException(HttpStatus.valueOf(500), "Длительность не может быть меньше или равна 0.");
        }
        if (film.getMpa() == null) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Rating не может быть null");
        }
    }

}
