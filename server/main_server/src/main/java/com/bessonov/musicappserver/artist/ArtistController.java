package com.bessonov.musicappserver.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping("/get/all")
    public List<ArtistInfoDTO> getAll(Authentication authentication) {
        return artistService.getArtistInfoAll(authentication.getName());
    }

    @GetMapping("/get/{artistId}")
    public ArtistInfoDTO getByArtistId(@PathVariable Integer artistId, Authentication authentication) {
        return artistService.getArtistInfoDTOByArtistId(authentication.getName(), artistId);
    }

    @PostMapping("/get/list")
    public List<ArtistInfoDTO> getByArtistIdList(@RequestBody List<Integer> artistIdList, Authentication authentication) {
        return artistService.getArtistInfoDTOByArtistIdList(authentication.getName(), artistIdList);
    }
}
