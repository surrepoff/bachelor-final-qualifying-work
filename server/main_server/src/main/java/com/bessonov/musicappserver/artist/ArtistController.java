package com.bessonov.musicappserver.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @GetMapping("/get/all")
    public List<ArtistInfoDTO> getAll(Authentication authentication) {
        return artistService.getArtistInfoAll();
    }

    @PostMapping("/get/byArtistId")
    public ArtistInfoDTO getByArtistId(@RequestBody int artistId, Authentication authentication) {
        return artistService.getArtistInfoDTOByArtistId(artistId);
    }

    @PostMapping("/get/byArtistId/list")
    public List<ArtistInfoDTO> getByArtistIdList(@RequestBody List<Integer> artistIdList, Authentication authentication) {
        return artistService.getArtistInfoDTOByArtistIdList(artistIdList);
    }
}
