package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Positive(message = "User-id не может быть отрицательным")
    private Long id;
    @NotBlank(message = "Логин не должен быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не должен содержать пробелы")
    private String login;
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
    @NotBlank(message = "Email не должен быть пустым")
    @Pattern(regexp = ".+@.+", message = "Email должен содержать символ '@'")
    private String email;
    @NotNull(message = "День рождение не может быть null")
    @Past(message = "День рождение не может быть позже настоящего времени")
    private LocalDate birthday;
    private Set<Long> friendsIds = new HashSet<>();
}
