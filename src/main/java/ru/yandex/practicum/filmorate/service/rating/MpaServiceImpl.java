package ru.yandex.practicum.filmorate.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.storage.rating.MpaStorage;

import java.util.List;

@Service
public class MpaServiceImpl implements MpaService {

    private final MpaStorage mpaStorage;

    @Autowired
    public MpaServiceImpl(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    @Override
    public List<MpaDTO> getRating() {
        return MpaMapper.fromRatingListToRatingDTOList(mpaStorage.getAllRatings());
    }

    @Override
    public MpaDTO getRatingById(Integer id) {
        return MpaMapper.fromRatingToRatingDTO(mpaStorage.getRatingById(id));
    }
}
