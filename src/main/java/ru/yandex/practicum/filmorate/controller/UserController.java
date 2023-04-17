package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validation.UserValidator;

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
        ResponseEntity<List<UserDto>> response = new ResponseEntity<>(userService.readAllUsers(), HttpStatus.valueOf(200));
        return response;
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody User user) {
        if (UserValidator.validateUser(user)) {
            ResponseEntity<UserDto> response = new ResponseEntity<>(userService.updateUser(user), HttpStatus.valueOf(200));
            if (response.getBody().getId() == 0L) {
                return new ResponseEntity<>(UserMapper.fromUserToUserDto(user), HttpStatus.valueOf(500));
            }
            return response;
        }
        return new ResponseEntity<>(UserMapper.fromUserToUserDto(user), HttpStatus.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        if (UserValidator.validateUser(user)) {
            ResponseEntity<UserDto> response = new ResponseEntity<>(userService.addUser(user), HttpStatus.valueOf(200));

            return response;
        }
        return new ResponseEntity<>(UserMapper.fromUserToUserDto(user), HttpStatus.valueOf(500));
    }
}
