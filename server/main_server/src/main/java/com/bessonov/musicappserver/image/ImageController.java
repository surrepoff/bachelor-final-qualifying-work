package com.bessonov.musicappserver.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping("/api/image/album/{albumId}")
    public ResponseEntity<byte[]> getAlbumImage(@PathVariable int albumId) {
        return imageService.getAlbumImage(albumId);
    }

    @GetMapping("/api/image/artist/{artistId}")
    public ResponseEntity<byte[]> getArtistImage(@PathVariable int artistId) {
        return imageService.getArtistImage(artistId);
    }

    @GetMapping("/api/image/track/{trackId}")
    public ResponseEntity<byte[]> getTrackImage(@PathVariable int trackId) {
        return imageService.getTrackImage(trackId);
    }
}
