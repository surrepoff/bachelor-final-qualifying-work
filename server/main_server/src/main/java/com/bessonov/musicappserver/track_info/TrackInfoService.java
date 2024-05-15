package com.bessonov.musicappserver.track_info;

import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.album_artist.AlbumArtistRepository;
import com.bessonov.musicappserver.database.album_track.AlbumTrackRepository;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.artist_status.ArtistStatusRepository;
import com.bessonov.musicappserver.database.artist_track.ArtistTrackRepository;
import com.bessonov.musicappserver.database.genre.GenreRepository;
import com.bessonov.musicappserver.database.license.LicenseRepository;
import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackInfoService {
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

    public TrackInfoDTO getTrackInfoByTrackId(int track_id) {
        Optional<Track> track = trackRepository.findById(track_id);

        return track.map(this::getTrackInfoByTrack).orElse(null);
    }

    public List<TrackInfoDTO> getTrackInfoByListTrackId(List<Integer> list_track_id) {
        List<TrackInfoDTO> trackInfoDTOList = new ArrayList<>();

        for (Integer track_id : list_track_id) {
            trackInfoDTOList.add(getTrackInfoByTrackId(track_id));
        }

        return trackInfoDTOList;
    }

    public List<TrackInfoDTO> getTrackInfoAll() {
        List<Track> trackList = trackRepository.findAll();

        List<TrackInfoDTO> trackInfoDTOList = new ArrayList<>();

        for (Track track : trackList) {
            trackInfoDTOList.add(getTrackInfoByTrack(track));
        }

        return trackInfoDTOList;
    }

    public TrackInfoDTO getTrackInfoByTrack(Track track) {
        //
        return new TrackInfoDTO();
    }

}
