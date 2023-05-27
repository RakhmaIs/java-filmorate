package ru.yandex.practicum.filmorate.service.rating;

import ru.yandex.practicum.filmorate.dto.MpaDTO;

import java.util.List;

public interface MpaService {
    List<MpaDTO> getRating();

    MpaDTO getRatingById(Integer id);
}
