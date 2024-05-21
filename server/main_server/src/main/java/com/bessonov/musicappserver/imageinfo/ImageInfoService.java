package com.bessonov.musicappserver.imageinfo;

import com.bessonov.musicappserver.database.album.Album;
import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.albumArtist.AlbumArtistRepository;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrack;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrackRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.artistStatus.ArtistStatusRepository;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrackRepository;
import com.bessonov.musicappserver.database.genre.GenreRepository;
import com.bessonov.musicappserver.database.license.LicenseRepository;
import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ImageInfoService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    @Autowired
    private AlbumTrackRepository albumTrackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistStatusRepository artistStatusRepository;

    @Autowired
    private ArtistTrackRepository artistTrackRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private TrackRepository trackRepository;

    public ResponseEntity<byte[]> getAlbumImage(int albumId) {
        Optional<Album>album = albumRepository.findById(albumId);
        if (album.isPresent()) {
            try {
                String imagePath = "../../data/image/album/" + album.get().getImageFilename();
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
                String imagePath = "../../data/image/artist/" + artist.get().getImageFilename();
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
                    String imagePath = "../../data/image/album/" + album.get().getImageFilename();
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
