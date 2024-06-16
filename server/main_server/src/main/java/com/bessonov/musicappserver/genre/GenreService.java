package com.bessonov.musicappserver.genre;

import com.bessonov.musicappserver.database.genre.Genre;
import com.bessonov.musicappserver.database.genre.GenreDTO;
import com.bessonov.musicappserver.database.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Genre> genreList = genreRepository.findAll(sort);

        List<GenreDTO> genreDTOList = new ArrayList<>();

        for (Genre genre : genreList) {
            genreDTOList.add(new GenreDTO(genre));
        }

        return genreDTOList;
    }
}
