package com.bessonov.musicappserver.album;

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
}
