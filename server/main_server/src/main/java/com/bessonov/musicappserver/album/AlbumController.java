package com.bessonov.musicappserver.album;

import com.bessonov.musicappserver.database.userAlbum.UserAlbumDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping("/get/all")
    public List<AlbumInfoDTO> getAll(Authentication authentication) {
        return albumService.getAlbumInfoAll(authentication.getName());
    }

    @GetMapping("/get/{albumId}")
    public AlbumInfoDTO getByAlbumId(@PathVariable Integer albumId, Authentication authentication) {
        return albumService.getAlbumInfoByAlbumId(authentication.getName(), albumId);
    }

    @PostMapping("/get/list")
    public List<AlbumInfoDTO> getByAlbumIdList(@RequestBody List<Integer> albumIdList, Authentication authentication) {
        return albumService.getAlbumInfoByAlbumIdList(authentication.getName(),albumIdList);
    }

    @GetMapping("/add/{albumId}")
    public UserAlbumDTO addAlbumToUserList(@PathVariable Integer albumId, Authentication authentication) {
        return albumService.addAlbumToUserList(authentication.getName(), albumId);
    }

    @GetMapping("/remove/{albumId}")
    public UserAlbumDTO removeAlbumFromUserList(@PathVariable Integer albumId, Authentication authentication) {
        return albumService.removeAlbumFromUserList(authentication.getName(), albumId);
    }

    @PostMapping("/rate/{albumId}")
    public UserRatingDTO rateAlbum(@PathVariable Integer albumId, @RequestBody Integer rateId, Authentication authentication) {
        return albumService.rateAlbum(authentication.getName(), albumId, rateId);
    }
}
