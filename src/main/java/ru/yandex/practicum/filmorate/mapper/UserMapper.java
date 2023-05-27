package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDTO fromUserToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .birthday(user.getBirthday())
                .login(user.getLogin())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User fromUserFtoToUser(UserDTO userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .birthday(userDto.getBirthday())
                .login(userDto.getLogin())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .friendsIds(userDto.getFriendsIds())
                .build();
    }

    public static List<UserDTO> fromListUsersToListUsersDto(Collection<User> users) {
        List<UserDTO> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(fromUserToUserDto(user));
        }
        return usersDto;
    }
}
