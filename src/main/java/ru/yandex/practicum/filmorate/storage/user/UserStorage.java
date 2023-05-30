package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    List<User> readAllUsers();

    User getUserById(Long id);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> findFriendsByUserId(Long userId);

    boolean userExists(Long userID);

    List<User> findCommonFriendsList(Long userId, Long friendId);
}
