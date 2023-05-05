package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validation.UserValidator;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> readAllUsers() {
        return new ResponseEntity<>(userService.readAllUsers(), HttpStatus.valueOf(200));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody User user) {
        UserValidator.validateUser(user);
        UserDto userDto = userService.updateUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        UserValidator.validateUser(user);
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<UserDto> addFriend(@PathVariable(name = "id") @PositiveOrZero Long userId,
                                             @PathVariable @PositiveOrZero Long friendId) {
        return new ResponseEntity<>(userService.addFriend(userId, friendId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<UserDto> deleteFriend(@PathVariable(name = "id") Long userId,
                                                @PathVariable Long friendId) {
        return new ResponseEntity<>(userService.deleteFriend(userId, friendId), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<?> findFriendsList(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findFriendsList(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<UserDto>> findCommonFriendsList(@PathVariable(name = "id") Long userId,
                                                               @PathVariable Long otherId) {
        return new ResponseEntity<>(userService.findCommonFriendsList(userId, otherId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUsersById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUsersById(id), HttpStatus.OK);
    }
}
