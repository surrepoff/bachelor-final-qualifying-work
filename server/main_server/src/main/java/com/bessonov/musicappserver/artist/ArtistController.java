package com.bessonov.musicappserver.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @GetMapping("/api/artist/get/all")
    public List<ArtistInfoDTO> getAll() {
        return artistService.getArtistInfoAll();
    }

    @PostMapping("/api/artist/get/byArtistId")
    public ArtistInfoDTO getByArtistId(@RequestBody int artistId) {
        return artistService.getArtistInfoDTOByArtistId(artistId);
    }

    @PostMapping("/api/artist/get/byArtistId/list")
    public List<ArtistInfoDTO> getByArtistIdList(@RequestBody List<Integer> artistIdList) {
        return artistService.getArtistInfoDTOByArtistIdList(artistIdList);
    }
}
