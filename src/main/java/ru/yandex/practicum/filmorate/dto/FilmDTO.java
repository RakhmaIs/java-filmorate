package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {
    @Positive(message = "id не может быть отрицательным")
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длинна описания 200 символов")
    private String description;
    @DateTimeFormat(pattern = "1895-12-28")
    @PastOrPresent(message = "Дата не может быть раньше 1895-12-28")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    private Integer duration;
    @NotNull(message = "Рейтинг не может быть null")
    private Mpa mpa;
    private Set<Genre> genres = new HashSet<>();
    private Set<Long> likesIds = new HashSet<>();
}

