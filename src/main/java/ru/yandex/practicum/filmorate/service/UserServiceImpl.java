package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
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

    @Override
    public UserDto getUsersById(Long id) {
        return UserMapper.fromUserToUserDto(userStorage.getUser(id));
    }

    @Override
    public UserDto addFriend(Long userId, Long friendId) {
        User user1 = userStorage.getUser(userId);
        User user2 = userStorage.getUser(friendId);
        user1.getFriendsIds().add(friendId);
        user2.getFriendsIds().add(userId);

        return UserMapper.fromUserToUserDto(user1);
    }

    @Override
    public UserDto deleteFriend(Long userId, Long friendId) {
        User user1 = userStorage.getUser(userId);
        User user2 = userStorage.getUser(friendId);
        if (user1.getFriendsIds().contains(friendId)) {
            user1.getFriendsIds().remove(friendId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой друг не найден");
        }
        if (user2.getFriendsIds().contains(userId)) {
            user2.getFriendsIds().remove(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой друг не найден");
        }
        return UserMapper.fromUserToUserDto(user1);
    }

    @Override
    public List<UserDto> findCommonFriendsList(Long userId, Long otherId) {
        Set<Long> commonFriends = new HashSet<>(userStorage.getUser(userId).getFriendsIds());
        commonFriends.retainAll(userStorage.getUser(otherId).getFriendsIds());
        return UserMapper.fromListUsersToListUsersDto(commonFriends.stream().map(userStorage::getUser).collect(Collectors.toList()));
    }

    @Override
    public List<UserDto> findFriendsList(Long id) {
        Set<Long> friends = new HashSet<>(userStorage.getUser((id)).getFriendsIds());
        return UserMapper.fromListUsersToListUsersDto(friends.stream().map(userStorage::getUser).collect(Collectors.toList()));

    }
}
