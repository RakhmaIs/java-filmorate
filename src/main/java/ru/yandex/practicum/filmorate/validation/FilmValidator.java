package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    private FilmValidator() {
    }

    public static boolean validateFilm(Film film) {
        if (film == null || film.getDescription() == null || film.getReleaseDate() == null) {
            return false;
        }
        if (film.getName().isBlank()) {
            return false;
        }
        if (film.getDescription().length() > 200) {
            return false;
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            return false;
        }
        if (film.getDuration() < 0) {
            return false;
        }
        return true;

    }

}
