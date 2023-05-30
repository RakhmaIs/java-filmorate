package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.validation.UserValidator;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> readAllUsers() {
        log.info("Получен запрос на получение всех пользователей.");
        return new ResponseEntity<>(userService.readAllUsers(), HttpStatus.valueOf(200));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        log.info("Получен запрос на обновление пользователя : {}.", user);
        UserValidator.validateUser(user);
        UserDTO userDto = userService.updateUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        log.info("Получен запрос на добавление пользователя : {}.", user);
        UserValidator.validateUser(user);
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable(name = "id") @PositiveOrZero Long userId,
                                       @PathVariable @PositiveOrZero Long friendId) {
        log.info("Получен запрос на добавление друга c id {} , к пользователю с id {}", friendId, userId);
        userService.addFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable(name = "id") Long userId,
                                          @PathVariable Long friendId) {
        log.info("Получен запрос на удаление друга с id {} , у пользователя с id {}", friendId, userId);
        userService.deleteFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<?> findFriendsListByUserId(@PathVariable Long id) {
        log.info("Получен запрос на получение друзей пользователя c id {}", id);
        return new ResponseEntity<>(userService.findFriendsByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<UserDTO>> findCommonFriendsList(@PathVariable(name = "id") Long userId,
                                                               @PathVariable Long otherId) {
        log.info("Получен запрос на получение общих друзей пользователя с id {} и второго пользователя с id {}.", userId, otherId);
        return new ResponseEntity<>(userService.findCommonFriendsList(userId, otherId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUsersById(@PathVariable Long id) {
        log.info("Запрос на получение пользователя с id {} получен.", id);
        return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
    }
}
