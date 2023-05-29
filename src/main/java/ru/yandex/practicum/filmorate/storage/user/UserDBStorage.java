package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository("UserDBStorage")
public class UserDBStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User addUser(User user) {
        String sqlQuery = "INSERT INTO users(login,user_name,email,birthday) VALUES(?,?,?,?)";
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Number id = jdbcInsert.executeAndReturnKey(Map.of("login", user.getLogin(),
                "user_name", user.getName(),
                "email", user.getEmail(),
                "birthday", user.getBirthday()));
        user.setId(id.longValue());
        log.info("Метод 'addUser(User user)' успешно выполнен. Данные пользовател успешно добавлены: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!userExists(user.getId())) {
            log.error("Ошибка выполнения метода 'updateUser(User user)'. Пользователь: '{}' не найден", user);
            throw new UserNotFoundException("User с таким id - не найден");
        }
        String sqlQuery = "UPDATE users SET login = ?, user_name = ?, email = ?, birthday = ?" +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        log.info("Метод 'updateUser(User user)' успешно выполнен. Данные пользовател успешно обновлены: {}", user);

        return user;
    }

    @Override
    public boolean userExists(Long userID) {
        String sqlQuery = "SELECT * FROM users WHERE user_id = ?";
        try {
            jdbcTemplate.queryForObject(sqlQuery, this::mapToUSer, userID);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<User> readAllUsers() {
        String sqlQuery = "SELECT * FROM users";
        List<User> allUsers = jdbcTemplate.query(sqlQuery, this::mapToUSer);
        log.info("Метод readAllUsers() успешно выполнен");
        return allUsers;

    }

    @Override
    public User getUserById(Long id) {
        if (!userExists(id)) {
            log.error("Ошибка выполнения метода getUserById(Long id) для пользователя с id {}", id);
            throw new UserNotFoundException("Пользователь с таким id - не найден");
        }
        String sqlQuery = "SELECT * FROM users WHERE user_id = ?";
        User user = jdbcTemplate.queryForObject(sqlQuery, this::mapToUSer, id);
        log.info("Метод getUserById(Long id) успешно выполнен для пользователя с id {}", id);
        return user;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if (!userExists(userId) || !userExists(friendId)) {
            log.error("Ошибка выполнения метода getAddFriend(Long userId,Long friendId) для пользователей с id = {} и friendId = {}", userId, friendId);
            throw new UserNotFoundException("Пользователь или друг с таки id не найден");
        }
        String sqlQuery = "INSERT INTO friends(user_id,friend_id) VALUES(?,?)";
        try {
            jdbcTemplate.update(sqlQuery, userId, friendId);
            log.info("Метод getAddFriend(Long userId,Long friendId) успешно выполнен для пользователей с id = {} и friendId = {}", userId, friendId);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Ошибка добавления пользователя c id =" + friendId + " в друзья к пользователю c id =" + userId);
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        if (!userExists(userId) || !userExists(friendId)) {
            log.error("Ошибка выполнения метода deleteFriend(Long userId,Long friendId) для пользователей с id = {} и friendId = {}", userId, friendId);
            throw new UserNotFoundException("Пользователь или друг с таки id не найден");
        }
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        try {
            jdbcTemplate.update(sqlQuery, userId, friendId);
            log.info("Метод deleteFriend(Long userId,Long friendId) успешно выполнен для пользователей с id = {} и friendId = {}", userId, friendId);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Ошибка добавления пользователя c id = " + friendId + "в друзья к пользователю c id = " + userId);
        }
    }

    public List<User> findFriendsByUserId(Long userId) {
        if (!userExists(userId)) {
            log.error("Ошибка выполнения метода findFriendsByUserId(Long userId) для пользователя с id {}", userId);
            throw new UserNotFoundException("Пользователь с таким id не найден");
        }
        String sqlQuery = "SELECT u.* FROM users u \n" +
                "INNER JOIN friends f ON u.user_id = f.friend_id \n" +
                "WHERE f.user_id = ?";
        List<User> friends = jdbcTemplate.query(sqlQuery, this::mapToUSer, userId);
        log.info("Метод findFriendsByUserId(Long userId) успешно выполнен для пользователя с id {}", userId);
        return friends;
    }

    public List<User> findCommonFriendsList(Long userId, Long friendId) {
        if (!userExists(userId) || !userExists(friendId)) {
            log.error("Ошибка выполнения метода findCommonFriendsList(Long userId, Long friendId) для пользователей с id = {} и friendId = {}", userId, friendId);
            throw new UserNotFoundException("Пользователь или друг с таки id не найден");
        }
        List<User> friendListOne = findFriendsByUserId(userId);
        List<User> friendListTwo = findFriendsByUserId(friendId);

        friendListOne.retainAll(friendListTwo);
        log.info("Метод findCommonFriendsList(Long userId,Long friendId) успешно выполнен для пользователей с id = {} и friendId = {}", userId, friendId);
        return friendListOne;
    }


    private User mapToUSer(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .email(resultSet.getString("email"))
                .name(resultSet.getString("user_name"))
                .id(resultSet.getLong("user_id"))
                .login(resultSet.getString("login"))
                .build();
    }
}
