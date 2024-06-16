package com.bessonov.musicappserver.genre;

import com.bessonov.musicappserver.database.genre.GenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/get/all")
    public List<GenreDTO> getAll() {
        return genreService.getAll();
    }
}
