package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(User user);

    UserDto updateUser(User user);

    List<UserDto> readAllUsers();
}
