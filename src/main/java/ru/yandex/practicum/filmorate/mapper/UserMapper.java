package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDto fromUserToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .birthday(user.getBirthday())
                .login(user.getLogin())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User fromUserFtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .birthday(userDto.getBirthday())
                .login(userDto.getLogin())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static List<UserDto> fromListUsersToListUsersDto(Collection<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(fromUserToUserDto(user));
        }
        return usersDto;
    }
}
