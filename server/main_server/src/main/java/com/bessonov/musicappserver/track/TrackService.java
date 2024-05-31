package com.bessonov.musicappserver.track;

import com.bessonov.musicappserver.database.album.Album;
import com.bessonov.musicappserver.database.album.AlbumDTO;
import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrack;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrackRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistDTO;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrack;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrackRepository;
import com.bessonov.musicappserver.database.genre.Genre;
import com.bessonov.musicappserver.database.genre.GenreDTO;
import com.bessonov.musicappserver.database.genre.GenreRepository;
import com.bessonov.musicappserver.database.license.License;
import com.bessonov.musicappserver.database.license.LicenseDTO;
import com.bessonov.musicappserver.database.license.LicenseRepository;
import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackDTO;
import com.bessonov.musicappserver.database.track.TrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumTrackRepository albumTrackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistTrackRepository artistTrackRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Value("${filepath.data.track}")
    private String filepath;

    public TrackInfoDTO getTrackInfoByTrackId(int trackId) {
        Optional<Track> track = trackRepository.findById(trackId);

        return track.map(this::getTrackInfoByTrack).orElse(null);
    }

    public List<TrackInfoDTO> getTrackInfoByTrackIdList(List<Integer> trackIdList) {
        List<TrackInfoDTO> trackInfoDTOList = new ArrayList<>();

        for (Integer trackId : trackIdList) {
            trackInfoDTOList.add(getTrackInfoByTrackId(trackId));
        }

        return trackInfoDTOList;
    }

    public List<TrackInfoDTO> getTrackInfoAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Track> trackList = trackRepository.findAll(sort);

        List<TrackInfoDTO> trackInfoDTOList = new ArrayList<>();

        for (Track track : trackList) {
            trackInfoDTOList.add(getTrackInfoByTrack(track));
        }

        return trackInfoDTOList;
    }

    public TrackInfoDTO getTrackInfoByTrack(Track track) {
        TrackInfoDTO trackInfoDTO = new TrackInfoDTO();

        trackInfoDTO.setTrack(new TrackDTO(track));

        List<ArtistDTO> artistDTOList = new ArrayList<>();
        List<ArtistDTO> featuredArtistDTOList = new ArrayList<>();
        List<ArtistTrack> artistTrackList = artistTrackRepository.findByIdTrackIdOrderByIdArtistIdAsc(track.getId());
        for (ArtistTrack artistTrack : artistTrackList) {
            Optional<Artist> artist = artistRepository.findById(artistTrack.getId().getArtistId());
            ArtistDTO artistDTO = artist.map(ArtistDTO::new).orElseGet(ArtistDTO::new);
            switch (artistTrack.getArtistStatusId()) {
                case 0:
                    artistDTOList.add(0, artistDTO);
                    break;
                case 1:
                    artistDTOList.add(artistDTO);
                    break;
                case 2:
                    featuredArtistDTOList.add(artistDTO);
                    break;
                default:
                    break;
            }
        }
        trackInfoDTO.setArtist(artistDTOList);
        trackInfoDTO.setFeaturedArtist(featuredArtistDTOList);

        List<AlbumDTO> albumDTOList = new ArrayList<>();
        List<AlbumTrack> albumTrackList = albumTrackRepository.findByIdTrackIdOrderByIdAlbumIdAsc(track.getId());
        for (AlbumTrack albumTrack : albumTrackList) {
            Optional<Album> album = albumRepository.findById(albumTrack.getId().getAlbumId());
            AlbumDTO albumDTO = album.map(AlbumDTO::new).orElseGet(AlbumDTO::new);
            albumDTOList.add(albumDTO);
        }
        trackInfoDTO.setAlbum(albumDTOList);

        Optional<Genre> genre = genreRepository.findById(track.getPrimaryGenreId());
        GenreDTO genreDTO = genre.map(GenreDTO::new).orElseGet(GenreDTO::new);
        trackInfoDTO.setGenre(genreDTO);

        Optional<License> license = licenseRepository.findById(track.getLicenseId());
        LicenseDTO licenseDTO = license.map(LicenseDTO::new).orElseGet(LicenseDTO::new);
        trackInfoDTO.setLicense(licenseDTO);

        return trackInfoDTO;
    }

    public ResponseEntity<Resource> streamAudio(int trackId){
        Optional<Track> track = trackRepository.findById(trackId);
        if (track.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            FileSystemResource file = new FileSystemResource(filepath + track.get().getAudioFilename());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
            headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<Resource> downloadFile(int trackId) {
        Optional<Track> track = trackRepository.findById(trackId);
        if (track.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            FileSystemResource file = new FileSystemResource(filepath + track.get().getAudioFilename());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + track.get().getAudioFilename());
            headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
