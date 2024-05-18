package com.bessonov.musicappserver.artistInfo;

import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.albumArtist.AlbumArtist;
import com.bessonov.musicappserver.database.albumArtist.AlbumArtistRepository;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrackRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistDTO;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.artistStatus.ArtistStatusRepository;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrack;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrackRepository;
import com.bessonov.musicappserver.database.genre.GenreRepository;
import com.bessonov.musicappserver.database.license.LicenseRepository;
import com.bessonov.musicappserver.database.track.TrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistInfoService {
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

    public ArtistInfoDTO getArtistInfoDTOByArtistId(int artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);

        return artist.map(this::getArtistInfoByArtist).orElse(null);
    }

    public List<ArtistInfoDTO> getArtistInfoDTOByArtistIdList(List<Integer> artistIdList) {
        List<ArtistInfoDTO> artistInfoDTOList = new ArrayList<>();

        for (Integer artistId : artistIdList) {
            artistInfoDTOList.add(getArtistInfoDTOByArtistId(artistId));
        }

        return artistInfoDTOList;
    }

    public List<ArtistInfoDTO> getArtistInfoAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Artist> artistList = artistRepository.findAll(sort);

        List<ArtistInfoDTO> artistInfoDTOList = new ArrayList<>();

        for (Artist artist : artistList) {
            artistInfoDTOList.add(getArtistInfoByArtist(artist));
        }

        return artistInfoDTOList;
    }

    public ArtistInfoDTO getArtistInfoByArtist(Artist artist) {
        ArtistInfoDTO artistInfoDTO = new ArtistInfoDTO();

        artistInfoDTO.setArtist(new ArtistDTO(artist));

        List<Integer> albumId = new ArrayList<>();
        List<AlbumArtist> albumArtistList = albumArtistRepository.findByIdArtistIdOrderByIdAlbumIdAsc(artist.getId());
        for (AlbumArtist albumArtist : albumArtistList) {
            albumId.add(albumArtist.getId().getAlbumId());
        }
        artistInfoDTO.setAlbumId(albumId);

        List<Integer> trackId = new ArrayList<>();
        List<ArtistTrack> artistTrackList = artistTrackRepository.findByIdArtistIdOrderByIdTrackIdAsc(artist.getId());
        for (ArtistTrack artistTrack : artistTrackList) {
            trackId.add(artistTrack.getId().getTrackId());
        }
        artistInfoDTO.setTrackId(trackId);

        return artistInfoDTO;
    }
}
