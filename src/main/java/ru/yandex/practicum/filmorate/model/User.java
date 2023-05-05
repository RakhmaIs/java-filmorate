package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
    private Set<Long> friendsIds = new HashSet<>();

}
