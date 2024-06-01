package com.bessonov.musicappserver.image;

import com.bessonov.musicappserver.database.album.Album;
import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrack;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrackRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumTrackRepository albumTrackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Value("${filepath.data.image}")
    private String filepath;

    public ResponseEntity<byte[]> getAlbumImage(int albumId) {
        Optional<Album>album = albumRepository.findById(albumId);
        if (album.isPresent()) {
            try {
                String imagePath = filepath + album.get().getImageFilename();
                Path path = Paths.get(imagePath);
                byte[] image = Files.readAllBytes(path);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            }
            catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<byte[]> getArtistImage(int artistId) {
        Optional<Artist>artist = artistRepository.findById(artistId);
        if (artist.isPresent()) {
            try {
                String imagePath = filepath + artist.get().getImageFilename();
                Path path = Paths.get(imagePath);
                byte[] image = Files.readAllBytes(path);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            }
            catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<byte[]> getTrackImage(int trackId) {
        Optional<Track>track = trackRepository.findById(trackId);
        Optional<AlbumTrack>albumTrack = albumTrackRepository.findFirstByIdTrackIdOrderByIdAlbumIdAsc(trackId);
        if (track.isPresent() && albumTrack.isPresent()) {
            Optional<Album>album = albumRepository.findById(albumTrack.get().getId().getAlbumId());
            if (album.isPresent()) {
                try {
                    String imagePath = filepath + album.get().getImageFilename();
                    Path path = Paths.get(imagePath);
                    byte[] image = Files.readAllBytes(path);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    return new ResponseEntity<>(image, headers, HttpStatus.OK);
                }
                catch (IOException e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
