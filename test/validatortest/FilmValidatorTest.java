package validatortest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidator;
import java.time.LocalDate;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;


class FilmValidatorTest {

    @Test
    public void doesNotThrowExceptionWhenFilmIsValid() {
        Film validFilm = new Film(222L, "Matrix", "The Matrix is a 1999 science fiction action film[5][6] ",
                LocalDate.of(1999, 3, 31), 30000, new HashSet<>());
        assertDoesNotThrow(() -> FilmValidator.validateFilm(validFilm));

    }

    @Test
    public void shouldThrowValidateExceptionWhenFilmEqualsNull() {
        Film invalidFilm = null;
        ValidationException ex = assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(null));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Поле не может быть null" + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionIfFilmNameIsBlank() {
        Film blankName = new Film(111L, " ", "Notice", LocalDate.of(1900, 12, 8), 600, new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(blankName));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Имя не может быть пустым." + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionIfFilmDescriptionLengthMoreThen200() {
        Film bigDescription = new Film(222L, "Matrix", "The Matrix is a 1999 science fiction action film[5][6] " +
                "written and directed by the Wachowskis.[a] It is the first installment in the Matrix film series, starring Keanu Reeves, "
                + "Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving, and Joe Pantoliano, and depicts a dystopian future in which humanity ",
                LocalDate.of(1999, 3, 31), 30000, new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(bigDescription));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Описание не может быть больше 200 - сот символов" + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionIfFilmReleaseDateIsBefore_1985_12_28() {
        Film invalidReleaseDate = new Film(333L, "Film From 1894", "A film from 1894,", LocalDate.of(1894, 3, 23), 5000, new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(invalidReleaseDate));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Дата релиза не может быть раньше 28.12.1895" + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfFilmDurationIsLessThenZero() {
        Film negativeDuration = new Film(444L, "ZeroDurationFilm", "Film without Duration", LocalDate.of(2022, 4, 18), -1, new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(negativeDuration));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Длительность не может быть меньше или равна 0." + "\"", ex.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfFilmDurationEqualsZero() {
        Film negativeDuration = new Film(444L, "ZeroDurationFilm", "Film without Duration", LocalDate.of(2022, 4, 18), 0, new HashSet<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> FilmValidator.validateFilm(negativeDuration));
        assertEquals(HttpStatus.valueOf(500) + " " + "\"" + "Длительность не может быть меньше или равна 0." + "\"", ex.getMessage());
    }
}