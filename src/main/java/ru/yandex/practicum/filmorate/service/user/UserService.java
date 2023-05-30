package ru.yandex.practicum.filmorate.service.user;


import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    UserDTO addUser(User user);

    UserDTO updateUser(User user);

    List<UserDTO> readAllUsers();

    UserDTO getUsersById(Long id);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<UserDTO> findCommonFriendsList(Long userId, Long friendId);

    List<UserDTO> findFriendsByUserId(Long userId);


}
