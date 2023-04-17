package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    @Override
    public UserDto addUser(User user) {
        return userStorage.addUser(user);
    }

    @Override
    public UserDto updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public List<UserDto> readAllUsers() {
        return userStorage.readAllUsers();
    }
}
