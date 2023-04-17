package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class UserDto {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;

}
