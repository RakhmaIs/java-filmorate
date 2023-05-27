package ru.yandex.practicum.filmorate.storage.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository("GenreDBStorage")
public class GenreDBStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addGenreToFilm(Film film) {
        String sqlQuery = "INSERT INTO film_genres(film_id,genre_id) VALUES(?,?)";
        for (Genre filmGenre : film.getGenres()) {
            jdbcTemplate.update(sqlQuery, film.getId(), filmGenre.getId());
        }
        log.info("Метод addGenreToFilm(Film film) успешно выполнен");
    }

    @Override
    public void deleteGenreFromFilm(Film film) {
        String sqlQuery = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        log.info("Метод deleteGenreFromFilm(Film film) успешно выполнен");
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sqlQuery = "SELECT * FROM genre WHERE genre_id = ?";
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> createGenre(rs, rowNum), id);
            log.info("Метод getGenreById(Integer id) успешно выполнен для жанра с id {}", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Ошибка выполнения getGenreById(Integer id) для жанра с id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанра с таким id не существует");
        }
        return genre;
    }

    @Override
    public List<Genre> getAllGenres() { // ++ DTO
        String sqlQuery = "SELECT * FROM genre";
        List<Genre> allGenres;
        try {
            allGenres = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createGenre(rs, rowNum));
            log.info("Метод getAllGenres() успешно выполнен.");
        } catch (DataAccessException e) {
            log.error("Ошибка выполнения getAllGenres().");
            throw new RuntimeException(e);
        }
        return allGenres;
    }

    @Override
    public List<Genre> getGenreByFilmId(Long id) {
        List<Genre> genres;
        String sqlQuery = "SELECT g.genre_id,g.genre_name FROM film_genres AS fa " +
                "INNER JOIN genre AS g ON fa.genre_id = g.genre_id WHERE film_id = ?"; // если что поменять местами алиасы в селекте
        try {
            genres = jdbcTemplate.query(sqlQuery, this::createGenre, id);
            log.info("Метод getGenreByFilmId(Long id) успешно выполнен для фильма с id {}", id);
            return genres;
        } catch (DataAccessException e) {
            log.error("Ошибка выполнения getGenreByFilmId(Long id). Для фильма с id {}", id);
            throw new RuntimeException(e);
        }
    }

    private Genre createGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("genre_id"))
                .name(rs.getString("genre_name"))
                .build();
    }
}
