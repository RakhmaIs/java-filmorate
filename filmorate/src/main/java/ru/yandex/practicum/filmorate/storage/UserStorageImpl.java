package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserStorageImpl implements UserStorage {

    private final Map<Long, User> usersMap = new HashMap<>();

    private Long idGen = 1L;

    @Override
    public UserDto addUser(User user) {
        user.setId(idGen);
        log.info("Пользователь " + user + " успешно добавлен");
        usersMap.put(idGen++, user);
        return UserMapper.fromUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(User user) {
        if (usersMap.containsKey(user.getId())) {
            usersMap.put(user.getId(), user);
            log.info("Пользователь " + user + " успешно обновлен");
            return UserMapper.fromUserToUserDto(user);
        }
        user.setId(0L);
        log.warn("Ошибка обновления пользователя");
        return UserMapper.fromUserToUserDto(user);
    }

    @Override
    public List<UserDto> readAllUsers() {
        log.info("Получен список пользователей");
        return UserMapper.fromListUsersToListUsersDto(usersMap.values());
    }
}
