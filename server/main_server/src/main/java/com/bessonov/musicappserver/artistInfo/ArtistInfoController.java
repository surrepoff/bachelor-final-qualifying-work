package com.bessonov.musicappserver.artistInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistInfoController {
    @Autowired
    ArtistInfoService artistInfoService;

    @GetMapping("/api/artistInfo/get/all")
    public List<ArtistInfoDTO> getAll() {
        return artistInfoService.getArtistInfoAll();
    }

    @PostMapping("/api/artistInfo/get/byArtistId")
    public ArtistInfoDTO getByArtistId(@RequestBody int artistId) {
        return artistInfoService.getArtistInfoDTOByArtistId(artistId);
    }

    @PostMapping("/api/artistInfo/get/byArtistId/list")
    public List<ArtistInfoDTO> getByArtistIdList(@RequestBody List<Integer> artistIdList) {
        return artistInfoService.getArtistInfoDTOByArtistIdList(artistIdList);
    }
}
