package com.bessonov.musicappserver.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @GetMapping("/api/album/get/all")
    public List<AlbumInfoDTO> getAll() {
        return albumService.getAlbumInfoAll();
    }

    @PostMapping("/api/album/get/byAlbumId")
    public AlbumInfoDTO getByAlbumId(@RequestBody int albumId) {
        return albumService.getAlbumInfoByAlbumId(albumId);
    }

    @PostMapping("/api/album/get/byAlbumId/list")
    public List<AlbumInfoDTO> getByAlbumIdList(@RequestBody List<Integer> albumIdList) {
        return albumService.getAlbumInfoByAlbumIdList(albumIdList);
    }
}
