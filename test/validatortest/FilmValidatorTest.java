package validatortest;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.validation.FilmValidator.validateFilm;

class FilmValidatorTest {

    @Test
    public void shouldReturnTrueWhenFilmIsValid() {
        Film film = new Film(222L, "Matrix", "The Matrix is a 1999 science fiction action film[5][6] ",
                LocalDate.of(1999, 3, 31), 30000);
        assertTrue(validateFilm(film));

    }

    @Test
    public void shouldReturnFalseWhenFilmEqualsNull() {
        assertFalse(validateFilm(null));
    }

    @Test
    public void shouldReturnFalseIfFilmNameIsBlank() {
        Film film = new Film(111L, " ", "Notice", LocalDate.of(1900, 12, 8), 600);
        assertFalse(validateFilm(film));
    }

    @Test
    public void shouldReturnFalseIfFilmDescriptionLengthMoreThen200() {
        Film film = new Film(222L, "Matrix", "The Matrix is a 1999 science fiction action film[5][6] " +
                "written and directed by the Wachowskis.[a] It is the first installment in the Matrix film series, starring Keanu Reeves, "
                + "Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving, and Joe Pantoliano, and depicts a dystopian future in which humanity ",
                LocalDate.of(1999, 3, 31), 30000);
        assertFalse(validateFilm(film));
    }

    @Test
    public void shouldReturnFalseIfFilmReleaseDateIsBefore_1985_12_28() {
        Film film = new Film(333L, "Film From 1894", "A film from 1894,", LocalDate.of(1894, 3, 23), 5000);
        assertFalse(validateFilm(film));
    }

    @Test
    public void shouldReturnFalseIfFilmDurationIsLessThenZero() {
        Film film = new Film(444L, "ZeroDurationFilm", "Film without Duration", LocalDate.of(2022, 4, 18), -1);
        assertFalse(validateFilm(film));
    }
}