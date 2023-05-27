package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreServiceImpl(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @Override
    public GenreDTO getGenreById(Integer id) {
        return GenreMapper.fromGenreToGenreDTO(genreStorage.getGenreById(id));
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return GenreMapper.fromGenreListToDTOList(genreStorage.getAllGenres());
    }

    @Override
    public List<GenreDTO> getGenreByFilmId(Long id) { //++ DTO
        return GenreMapper.fromGenreListToDTOList(genreStorage.getGenreByFilmId(id));
    }

    @Override
    public void addGenreToFilm(Film film) {
        genreStorage.addGenreToFilm(film);
    }

    @Override
    public void deleteGenreFromFilm(Film film) {
        genreStorage.deleteGenreFromFilm(film);
    }
}
