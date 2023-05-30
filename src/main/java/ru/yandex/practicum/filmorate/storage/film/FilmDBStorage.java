package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.genre.GenreService;
import ru.yandex.practicum.filmorate.service.rating.MpaService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Repository("FilmDBStorage")
public class FilmDBStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreService genreService;
    private final MpaService mpaService;
    private final UserStorage userStorage;

    @Autowired
    public FilmDBStorage(JdbcTemplate jdbcTemplate, GenreService genreService, MpaService mpaService, @Qualifier("UserDBStorage") UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreService = genreService;
        this.mpaService = mpaService;
        this.userStorage = userStorage;
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "INSERT INTO film(name,description,release_date,duration,rating_id) values (?,?,?,?,?)";
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("film_id");
        Number id = jdbcInsert.executeAndReturnKey(Map.of("name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "rating_id", film.getMpa().getId()));
        film.setId(id.longValue());
        for (Genre filmGenre : film.getGenres()) {
            filmGenre.setName(genreService.getGenreById(filmGenre.getId()).getName());
        }
        genreService.deleteGenreFromFilm(film);
        genreService.addGenreToFilm(film);
        log.info("Метод createFilm(Film film) успешно выполнен для фильма: {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!filmExists(film.getId())) {
            log.error("Ошибка выполнения метода 'updateFilm(Film film)'. Фильм : '{}'  - не найден", film);
            throw new FilmNotFoundException("Фильм с таким id - не найден");
        }
        String sqlQuery = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? WHERE  film_id = ?";
        if (jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()) != 0) {
            if (film.getGenres() != null && !film.getGenres().isEmpty()) {
                for (Genre genre : film.getGenres()) {
                    genre.setName(genreService.getGenreById(genre.getId()).getName());
                }
            }
            genreService.deleteGenreFromFilm(film);
            genreService.addGenreToFilm(film);
            film.setGenres(new HashSet<>(GenreMapper.fromGenreDTOListToList(genreService.getGenreByFilmId(film.getId()))));
            film.setMpa(MpaMapper.fromRatingDTOToRating(mpaService.getRatingById(film.getMpa().getId())));
        }
        log.info("Метод 'updateFilm(Film film)' успешно выполнен. Данные фильма успешно обновлены: {}", film);
        return film;
    }

    @Override
    public Film getFilmById(Long id) {
        Film film = null;
        if (id == null) {
            log.error("Ошибка выполнения метода getFilmById(Long id) для фильма с id {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такого айди не найдено.");
        }
        String sqlQuery = "SELECT * FROM film WHERE film_id = ? ";
        SqlRowSet filmRowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);
        if (filmRowSet.first()) {
            Mpa rating = MpaMapper.fromRatingDTOToRating(mpaService.getRatingById(filmRowSet.getInt("rating_id")));
            Set<Genre> genreSet = new HashSet<>(GenreMapper.fromGenreDTOListToList(genreService.getGenreByFilmId(filmRowSet.getLong("film_id"))));
            film = Film.builder()
                    .id(filmRowSet.getLong("film_id"))
                    .name(filmRowSet.getString("name"))
                    .description(filmRowSet.getString("description"))
                    .releaseDate(filmRowSet.getDate("release_date").toLocalDate())
                    .duration(filmRowSet.getInt("duration"))
                    .mpa(rating)
                    .genres(genreSet)
                    .build();
        } else {
            throw new FilmNotFoundException("Такой фильм не найден");
        }
        log.info("Метод getFilmById(Long id) успешно выполнен для фильма с id {}", id);
        return film;
    }

    @Override
    public boolean filmExists(Long filmId) {
        String sqlQuery = "SELECT * FROM film WHERE film_id = ?";
        try {
            jdbcTemplate.queryForObject(sqlQuery, this::mapTOFilm, filmId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<Film> readAllFilms() {
        List<Film> allFilms;
        String sqlQuery = "SELECT * FROM film";
        allFilms = jdbcTemplate.query(sqlQuery, this::mapTOFilm);
        log.info("Метод readAllFilms() успешно выполнен");
        return allFilms;
    }

    @Override
    public void deleteFilmById(Long id) {
        if (id == null) {
            log.error("Ошибка выполнения метода deleteFilmById(Long id) фильма с id = {} не существует", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с таким id не найден");
        }
        String sqlQuery = "DELETE FROM film WHERE film_id = ?";
        if (jdbcTemplate.update(sqlQuery, id) == 0) {
            throw new FilmNotFoundException("Фильм с таким id не найден.Обновление невозможно.");
        }
        log.info("Фильм с id {} успешно удален из базы данных", id);
    }

    public void addLikeToFilm(Long filmId, Long userId) {
        if (!filmExists(filmId)) {
            log.error("Ошибка выполнения метода addLikeToFilm(Long filmId, Long userId) фильма с id = {} не существует", filmId);
            throw new FilmNotFoundException("Фильм не найден");
        }
        if (!userStorage.userExists(userId)) {
            log.error("Ошибка выполнения метода addLikeToFilm(Long filmId, Long userId). Пользователь с id '{}' не найден", userId);
            throw new UserNotFoundException("Пользователь не найден");
        }
        String sqlQuery = "INSERT INTO likes(film_id,user_id) VALUES(?,?)";
        try {
            jdbcTemplate.update(sqlQuery, filmId, userId);
            log.info("Метод addLikeToFilm(Long filmId, Long userId) успешно выполнен для фильма с id {}", filmId);
        } catch (DataAccessException e) {
            throw new FilmNotFoundException(("Ошибка добавления фильму :" + filmId + " лайка от пользователя id=" + userId)); // -- потом поменять исключение
        }
    }

    @Override
    public void deleteLikeFromFilm(Long filmId, Long userId) {
        if (!filmExists(filmId)) {
            log.error("Ошибка выполнения метода deleteLikeFromFilm(Long filmId, Long userId) фильма с id = {} не существует", filmId);
            throw new FilmNotFoundException("Фильм не найден");
        }
        if (!userStorage.userExists(userId)) {
            log.error("Ошибка выполнения метода deleteLikeFromFilm(Long filmId, Long userId). Пользователь с id '{}' не найден", userId);
            throw new UserNotFoundException("Пользователь не найден");
        }
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        try {
            jdbcTemplate.update(sqlQuery, filmId, userId);
            log.info("Метод deleteLikeFromFilm(Long filmId, Long userId) успешно выполнен для фильма с id {}", filmId);
        } catch (DataAccessException e) {
            throw new FilmNotFoundException("Ошибка удаления лайка от пользователя id = " + userId + " из фильма id=" + userId);
        }
    }

    public List<Film> findPopularFilms(Integer count) { // замапить в сервисе
        String sqlQuery = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id FROM film AS f " +
                "LEFT JOIN likes AS l ON f.film_id = l.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?";
        try {
            List<Film> popularFilms = jdbcTemplate.query(sqlQuery, this::mapTOFilm, count);
            log.info("Метод findPopularFilms(Integer count) успешно выполнен ");
            return popularFilms;
        } catch (DataAccessException e) {
            log.error("Ошибка выполнения findPopularFilms(Integer count)");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private Film mapTOFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("film_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(MpaMapper.fromRatingDTOToRating(mpaService.getRatingById(resultSet.getInt("rating_id"))))
                .genres(new HashSet<>(GenreMapper.fromGenreDTOListToList(genreService.getGenreByFilmId(resultSet.getLong("film_id")))))
                .build();
    }
}

