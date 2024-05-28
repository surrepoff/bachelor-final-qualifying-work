package com.bessonov.musicappserver.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @GetMapping("/get/all")
    public List<AlbumInfoDTO> getAll() {
        return albumService.getAlbumInfoAll();
    }

    @PostMapping("/get/byAlbumId")
    public AlbumInfoDTO getByAlbumId(@RequestBody int albumId) {
        return albumService.getAlbumInfoByAlbumId(albumId);
    }

    @PostMapping("/get/byAlbumId/list")
    public List<AlbumInfoDTO> getByAlbumIdList(@RequestBody List<Integer> albumIdList) {
        return albumService.getAlbumInfoByAlbumIdList(albumIdList);
    }
}
