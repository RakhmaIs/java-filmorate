package ru.yandex.practicum.filmorate.storage.rating;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository("RatingDBStorage")
public class MpaDBStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getRatingById(Integer id) {
        Mpa rating;
        String sqlQuery = "SELECT * FROM rating WHERE rating_id = ?";
        try {
            rating = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> createRating(rs, rowNum), id);
            log.info("Метод getRatingById(Integer id успешно выполнен. Для рейтинга с id {}", id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Ошибка выполнения getRatingById(Integer id) для рейтинга с id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating с таким id не найден");
        }
        return rating;
    }

    @Override
    public List<Mpa> getAllRatings() {
        String sqlQuery = "SELECT * FROM rating";
        List<Mpa> allRatings;
        try {
            allRatings = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createRating(rs, rowNum));
            log.info("Метод getAllRatings() успешно выполнен.");
        } catch (DataAccessException e) {
            log.error("Ошибка выполнения getAllRatings().");
            throw new RuntimeException(e);
        }
        return allRatings;
    }

    private Mpa createRating(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("rating_id"))
                .name(rs.getString("rating_name"))
                .build();
    }
}
