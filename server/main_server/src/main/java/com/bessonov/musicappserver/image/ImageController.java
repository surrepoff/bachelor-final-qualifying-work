package com.bessonov.musicappserver.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/album/{albumId}")
    public ResponseEntity<byte[]> getAlbumImage(@PathVariable Integer albumId) {
        return imageService.getAlbumImage(albumId);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<byte[]> getArtistImage(@PathVariable Integer artistId) {
        return imageService.getArtistImage(artistId);
    }

    @GetMapping("/track/{trackId}")
    public ResponseEntity<byte[]> getTrackImage(@PathVariable Integer trackId) {
        return imageService.getTrackImage(trackId);
    }
}
