package com.bessonov.musicappserver.imageinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageInfoController {
    @Autowired
    ImageInfoService imageInfoService;

    @GetMapping("/api/image/album/{albumId}")
    public ResponseEntity<byte[]> getAlbumImage(@PathVariable int albumId) {
        return imageInfoService.getAlbumImage(albumId);
    }

    @GetMapping("/api/image/artist/{artistId}")
    public ResponseEntity<byte[]> getArtistImage(@PathVariable int artistId) {
        return imageInfoService.getArtistImage(artistId);
    }

    @GetMapping("/api/image/track/{trackId}")
    public ResponseEntity<byte[]> getTrackImage(@PathVariable int trackId) {
        return imageInfoService.getTrackImage(trackId);
    }
}
