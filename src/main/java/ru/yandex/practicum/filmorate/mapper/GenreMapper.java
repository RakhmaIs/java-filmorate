package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreMapper {


    public static GenreDTO fromGenreToGenreDTO(Genre genre) {
        if (genre == null) {
            return null;
        }
        return GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public static List<GenreDTO> fromGenreListToDTOList(List<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream().map(GenreMapper::fromGenreToGenreDTO).collect(Collectors.toList());
    }

    public static Genre fromGenreDTOToGenre(GenreDTO genreDTO) {
        if (genreDTO == null) {
            return null;
        }
        return Genre.builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .build();
    }

    public static List<Genre> fromGenreDTOListToList(List<GenreDTO> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream().map(GenreMapper::fromGenreDTOToGenre).collect(Collectors.toList());
    }
}
