package ru.yandex.practicum.filmorate.validation;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {
    private UserValidator() {
    }

    public static void validateUser(User user) {
        if (user == null || user.getLogin() == null || user.getEmail() == null) {
            throw new ValidationException(HttpStatus.valueOf(500), "Поле не может быть null.");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException(HttpStatus.valueOf(500), "Почта не может быть пустой и должна содержать символ '@'.");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException(HttpStatus.valueOf(500), "Логин не может быть пустым и логин не может сожержать пробелы.");
        }
        if (user.getBirthday() == null) {
            throw new ValidationException(HttpStatus.valueOf(500), "Поле день рождение не может быть null.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException(HttpStatus.valueOf(500), "День рождение не может быть позже настоящего времени.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
