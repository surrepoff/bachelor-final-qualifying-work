package com.bessonov.musicappserver.album;

import com.bessonov.musicappserver.database.album.Album;
import com.bessonov.musicappserver.database.album.AlbumDTO;
import com.bessonov.musicappserver.database.album.AlbumRepository;
import com.bessonov.musicappserver.database.albumArtist.AlbumArtist;
import com.bessonov.musicappserver.database.albumArtist.AlbumArtistRepository;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrack;
import com.bessonov.musicappserver.database.albumTrack.AlbumTrackRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistDTO;
import com.bessonov.musicappserver.database.artist.ArtistRepository;

import com.bessonov.musicappserver.database.userAlbum.UserAlbum;
import com.bessonov.musicappserver.database.userAlbum.UserAlbumDTO;
import com.bessonov.musicappserver.database.userAlbum.UserAlbumId;
import com.bessonov.musicappserver.database.userAlbum.UserAlbumRepository;
import com.bessonov.musicappserver.database.userAlbumRating.UserAlbumRating;
import com.bessonov.musicappserver.database.userAlbumRating.UserAlbumRatingRepository;
import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.bessonov.musicappserver.database.userRating.UserRating;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    @Autowired
    private AlbumTrackRepository albumTrackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserAlbumRepository userAlbumRepository;

    @Autowired
    private UserAlbumRatingRepository userAlbumRatingRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    public AlbumInfoDTO getAlbumInfoByAlbumId(String username, int albumId) {
        Optional<Album> album = albumRepository.findById(albumId);

        return album.map(value -> getAlbumInfoByAlbum(username, value)).orElse(null);
    }

    public List<AlbumInfoDTO> getAlbumInfoByAlbumIdList(String username, List<Integer> albumIdList) {
        List<AlbumInfoDTO> albumInfoDTOList = new ArrayList<>();

        for (Integer albumId : albumIdList) {
            albumInfoDTOList.add(getAlbumInfoByAlbumId(username, albumId));
        }

        return albumInfoDTOList;
    }

    public List<AlbumInfoDTO> getAlbumInfoAll(String username) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Album> albumList = albumRepository.findAll(sort);

        List<AlbumInfoDTO> albumInfoDTOList = new ArrayList<>();

        for (Album album : albumList) {
            albumInfoDTOList.add(getAlbumInfoByAlbum(username, album));
        }

        return albumInfoDTOList;
    }

    public AlbumInfoDTO getAlbumInfoByAlbum(String username, Album album) {
        AlbumInfoDTO albumInfoDTO = new AlbumInfoDTO();

        albumInfoDTO.setAlbum(new AlbumDTO(album));

        List<ArtistDTO> artistDTOList = new ArrayList<>();
        List<ArtistDTO> featuredArtistDTOList = new ArrayList<>();
        List<AlbumArtist> albumArtistList = albumArtistRepository.findByIdAlbumIdOrderByIdArtistIdAsc(album.getId());
        for (AlbumArtist albumArtist : albumArtistList) {
            Optional<Artist> artist = artistRepository.findById(albumArtist.getId().getArtistId());
            ArtistDTO artistDTO = artist.map(ArtistDTO::new).orElseGet(ArtistDTO::new);
            switch (albumArtist.getArtistStatusId()) {
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
        albumInfoDTO.setArtist(artistDTOList);
        albumInfoDTO.setFeaturedArtist(featuredArtistDTOList);

        List<Integer> trackId = new ArrayList<>();
        List<AlbumTrack> albumTrackList = albumTrackRepository.findByIdAlbumIdOrderByTrackNumberInAlbumAsc(album.getId());
        for (AlbumTrack albumTrack : albumTrackList) {
            trackId.add(albumTrack.getId().getTrackId());
        }
        albumInfoDTO.setTrackId(trackId);

        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isPresent()){
            UserAlbumId userAlbumId = new UserAlbumId(userData.get().getId(), album.getId());

            Optional<UserAlbumRating> userAlbumRating = userAlbumRatingRepository.findById(userAlbumId);
            UserRatingDTO userRatingDTO = new UserRatingDTO();

            if (userAlbumRating.isPresent()) {
                Optional<UserRating> userRating = userRatingRepository.findById(userAlbumRating.get().getUserRatingId());
                if (userRating.isPresent()) {
                    userRatingDTO = new UserRatingDTO(userRating.get());
                }
            }
            albumInfoDTO.setRating(userRatingDTO);

            Optional<UserAlbum> userAlbum = userAlbumRepository.findById(userAlbumId);
            UserAlbumDTO userAlbumDTO = new UserAlbumDTO();

            if (userAlbum.isPresent()) {
                userAlbumDTO.setAdded(true);
                userAlbumDTO.setAddedDate(userAlbum.get().getAddedDate());
            }
            albumInfoDTO.setIsAdded(userAlbumDTO);
        }

        return albumInfoDTO;
    }
}
