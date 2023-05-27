package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@Builder
public class GenreDTO {
    @Positive
    private Integer id;
    @NotBlank
    private String name;


}
