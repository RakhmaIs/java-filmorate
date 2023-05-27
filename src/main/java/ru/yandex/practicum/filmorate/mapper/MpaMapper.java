package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.stream.Collectors;

public class MpaMapper {

    public static MpaDTO fromRatingToRatingDTO(Mpa rating) {
        if (rating == null) {
            return null;
        }
        return MpaDTO.builder()
                .id(rating.getId())
                .name(rating.getName())
                .build();
    }

    public static List<MpaDTO> fromRatingListToRatingDTOList(List<Mpa> ratings) {
        if (ratings == null) {
            return null;
        }
        return ratings.stream().map(MpaMapper::fromRatingToRatingDTO).collect(Collectors.toList());
    }

    public static Mpa fromRatingDTOToRating(MpaDTO rating) {
        if (rating == null) {
            return null;
        }
        return Mpa.builder()
                .id(rating.getId())
                .name(rating.getName())
                .build();
    }
}
