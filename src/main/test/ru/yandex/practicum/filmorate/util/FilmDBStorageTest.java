package ru.yandex.practicum.filmorate.util;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmDBStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDBStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/schema.sql"),
        @Sql(scripts = {"/data.sql", "/test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class FilmDBStorageTest {

    FilmDBStorage filmDBStorage;

    @Test
    public void testDeleteFilm() {

        Optional<Integer> filmsOptionalSize = Optional.of(filmDBStorage.readAllFilms().size());
        assertThat(filmsOptionalSize)
                .isPresent()
                .hasValueSatisfying(size -> AssertionsForClassTypes.assertThat(size).isEqualTo(3));

        filmDBStorage.deleteFilmById(filmDBStorage.readAllFilms().get(0).getId());

        filmsOptionalSize = Optional.of(filmDBStorage.readAllFilms().size());
        assertThat(filmsOptionalSize)
                .isPresent()
                .hasValueSatisfying(size -> AssertionsForClassTypes.assertThat(size).isEqualTo(2));

    }

    @Test
    public void createFilmTest() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        Film film = new Film();
        film.setName("Невероятные приключения");
        film.setDescription("very nice");
        film.setReleaseDate(LocalDate.parse("2023-01-18"));
        film.setDuration(1024);
        film.setMpa(mpa);


        Optional<Film> filmOptional = Optional.ofNullable(filmDBStorage.createFilm(film));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(userId -> assertThat(film).hasFieldOrPropertyWithValue("id", 4L));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(userBirthday -> assertThat(film)
                        .hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2023, 01, 18)));

    }

    @Test
    public void updateFilm() {
        Optional<Film> filmOptional = Optional.ofNullable(filmDBStorage.readAllFilms().get(0));
        assertThat(filmOptional.get().getId()).isEqualTo(1);
        assertThat(filmOptional.get().getName()).isEqualTo("Безудержное веселье");

        filmOptional.get().setName("BulletProof");
        filmDBStorage.updateFilm(filmOptional.get());

        Optional<Film> filmWasUpdate = Optional.ofNullable(filmDBStorage.readAllFilms().get(0));

        assertThat(filmWasUpdate).isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("id", 1L));
        assertThat(filmWasUpdate).isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("name", "BulletProof"));
    }

    @Test
    public void readAllFilmsTest() {
        List<Film> filmList = filmDBStorage.readAllFilms();
        Optional<Integer> filmsOptionalSize = Optional.of(filmDBStorage.readAllFilms().size());
        assertThat(filmsOptionalSize)
                .isPresent()
                .hasValueSatisfying(size -> AssertionsForClassTypes.assertThat(size).isEqualTo(3));
    }
}