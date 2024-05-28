package com.bessonov.musicappserver.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @GetMapping("/get/all")
    public List<AlbumInfoDTO> getAll(Authentication authentication) {
        return albumService.getAlbumInfoAll();
    }

    @PostMapping("/get/byAlbumId")
    public AlbumInfoDTO getByAlbumId(@RequestBody int albumId, Authentication authentication) {
        return albumService.getAlbumInfoByAlbumId(albumId);
    }

    @PostMapping("/get/byAlbumId/list")
    public List<AlbumInfoDTO> getByAlbumIdList(@RequestBody List<Integer> albumIdList, Authentication authentication) {
        return albumService.getAlbumInfoByAlbumIdList(albumIdList);
    }
}
