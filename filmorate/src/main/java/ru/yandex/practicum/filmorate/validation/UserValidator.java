package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {
    private UserValidator() {

    }

    public static boolean validateUser(User user) {
        if (user == null || user.getLogin() == null || user.getEmail() == null) {
            return false;
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            return false;
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            return false;
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return !user.getBirthday().isAfter(LocalDate.now());
    }
}
