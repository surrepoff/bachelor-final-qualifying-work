package com.bessonov.musicappserver.albumInfo;

import com.bessonov.musicappserver.trackInfo.TrackInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumInfoController {
    @Autowired
    AlbumInfoService albumInfoService;

    @GetMapping("/api/albumInfo/get/all")
    public List<AlbumInfoDTO> getAll() {
        return albumInfoService.getAlbumInfoAll();
    }

    @PostMapping("/api/albumInfo/get/byAlbumId")
    public AlbumInfoDTO getByAlbumId(@RequestBody int albumId) {
        return albumInfoService.getAlbumInfoByAlbumId(albumId);
    }

    @PostMapping("/api/albumInfo/get/byAlbumId/list")
    public List<AlbumInfoDTO> getByAlbumIdList(@RequestBody List<Integer> albumIdList) {
        return albumInfoService.getAlbumInfoByAlbumIdList(albumIdList);
    }
}
