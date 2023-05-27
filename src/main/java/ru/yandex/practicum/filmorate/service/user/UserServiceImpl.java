package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("UserDBStorage") UserStorage userStorage) {
        this.userStorage = userStorage;

    }

    @Override
    public UserDTO addUser(User user) {
        return UserMapper.fromUserToUserDto(userStorage.addUser(user));
    }

    @Override
    public UserDTO updateUser(User user) {
        return UserMapper.fromUserToUserDto(userStorage.updateUser(user));
    }

    @Override
    public List<UserDTO> readAllUsers() {
        return UserMapper.fromListUsersToListUsersDto(userStorage.readAllUsers());
    }

    @Override
    public UserDTO getUsersById(Long id) {
        return UserMapper.fromUserToUserDto(userStorage.getUserById(id));
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        userStorage.addFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        userStorage.deleteFriend(userId, friendId);
    }

    @Override
    public List<UserDTO> findCommonFriendsList(Long userId, Long friendId) {
        return UserMapper.fromListUsersToListUsersDto(userStorage.findCommonFriendsList(userId, friendId));
    }

    @Override
    public List<UserDTO> findFriendsByUserId(Long userId) {
        return UserMapper.fromListUsersToListUsersDto(userStorage.findFriendsByUserId(userId));
    }
}
