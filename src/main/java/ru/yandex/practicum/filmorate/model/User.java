package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;

}
