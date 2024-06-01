package com.bessonov.musicappserver.artist;

import com.bessonov.musicappserver.database.userArtist.UserArtistDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
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
        return artistService.getAll(authentication.getName());
    }

    @GetMapping("/get/{artistId}")
    public ArtistInfoDTO getByArtistId(@PathVariable Integer artistId, Authentication authentication) {
        return artistService.getByArtistId(authentication.getName(), artistId);
    }

    @PostMapping("/get/list")
    public List<ArtistInfoDTO> getByArtistIdList(@RequestBody List<Integer> artistIdList, Authentication authentication) {
        return artistService.getByArtistIdList(authentication.getName(), artistIdList);
    }

    @GetMapping("/get/user")
    public List<ArtistInfoDTO> getArtistUserList(Authentication authentication) {
        return artistService.getArtistUserList(authentication.getName());
    }

    @GetMapping("/add/{artistId}")
    public UserArtistDTO addArtistToUserList(@PathVariable Integer artistId, Authentication authentication) {
        return artistService.addArtistToUserList(authentication.getName(), artistId);
    }

    @GetMapping("/remove/{artistId}")
    public UserArtistDTO removeArtistFromUserList(@PathVariable Integer artistId, Authentication authentication) {
        return artistService.removeArtistFromUserList(authentication.getName(), artistId);
    }

    @PostMapping("/rate/{artistId}")
    public UserRatingDTO rateArtist(@PathVariable Integer artistId, @RequestBody Integer rateId, Authentication authentication) {
        return artistService.rateArtist(authentication.getName(), artistId, rateId);
    }
}
