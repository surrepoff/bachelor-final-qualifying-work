package com.bessonov.musicappserver.artist;

import com.bessonov.musicappserver.database.albumArtist.AlbumArtist;
import com.bessonov.musicappserver.database.albumArtist.AlbumArtistRepository;
import com.bessonov.musicappserver.database.artist.Artist;
import com.bessonov.musicappserver.database.artist.ArtistDTO;
import com.bessonov.musicappserver.database.artist.ArtistRepository;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrack;
import com.bessonov.musicappserver.database.artistTrack.ArtistTrackRepository;
import com.bessonov.musicappserver.database.userArtist.UserArtist;
import com.bessonov.musicappserver.database.userArtist.UserArtistDTO;
import com.bessonov.musicappserver.database.userArtist.UserArtistId;
import com.bessonov.musicappserver.database.userArtist.UserArtistRepository;
import com.bessonov.musicappserver.database.userArtistRating.UserArtistRating;
import com.bessonov.musicappserver.database.userArtistRating.UserArtistRatingRepository;
import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.bessonov.musicappserver.database.userRating.UserRating;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ArtistService {
    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistTrackRepository artistTrackRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserArtistRepository userArtistRepository;

    @Autowired
    private UserArtistRatingRepository userArtistRatingRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;


    public ArtistInfoDTO getByArtistId(String username, int artistId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Artist> artist = artistRepository.findById(artistId);

        return artist.map(value -> getArtistInfoByArtist(username, value)).orElse(null);
    }

    public List<ArtistInfoDTO> getByArtistIdList(String username, List<Integer> artistIdList) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        List<ArtistInfoDTO> artistInfoDTOList = new ArrayList<>();

        for (Integer artistId : artistIdList) {
            artistInfoDTOList.add(getByArtistId(username, artistId));
        }

        return artistInfoDTOList;
    }

    public List<ArtistInfoDTO> getAll(String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Artist> artistList = artistRepository.findAll(sort);

        List<ArtistInfoDTO> artistInfoDTOList = new ArrayList<>();

        for (Artist artist : artistList) {
            artistInfoDTOList.add(getArtistInfoByArtist(username, artist));
        }

        return artistInfoDTOList;
    }

    public List<ArtistInfoDTO> getArtistUserList(String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        List<UserArtist> userArtistList = userArtistRepository.findByIdUserIdOrderByArtistNumberInUserListAsc(userData.get().getId());

        List<ArtistInfoDTO> artistInfoDTOList = new ArrayList<>();

        for (UserArtist userArtist : userArtistList) {
            artistInfoDTOList.add(getByArtistId(username, userArtist.getId().getArtistId()));
        }

        return artistInfoDTOList;
    }

    public ArtistInfoDTO getArtistInfoByArtist(String username, Artist artist) {
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

        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isPresent()) {
            UserArtistId userArtistId = new UserArtistId(userData.get().getId(), artist.getId());

            Optional<UserArtistRating> userArtistRating = userArtistRatingRepository.findById(userArtistId);
            UserRatingDTO userRatingDTO = new UserRatingDTO();

            if (userArtistRating.isPresent()) {
                Optional<UserRating> userRating = userRatingRepository.findById(userArtistRating.get().getUserRatingId());
                if (userRating.isPresent()) {
                    userRatingDTO = new UserRatingDTO(userRating.get());
                }
            }
            artistInfoDTO.setRating(userRatingDTO);

            Optional<UserArtist> userArtist = userArtistRepository.findById(userArtistId);
            UserArtistDTO userArtistDTO = new UserArtistDTO();

            if (userArtist.isPresent()) {
                userArtistDTO.setAdded(true);
                userArtistDTO.setAddedDate(userArtist.get().getAddedDate());
            }
            artistInfoDTO.setIsAdded(userArtistDTO);
        }

        return artistInfoDTO;
    }

    public ArtistInfoDTO addArtistToUserList(String username, int artistId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Artist> artist = artistRepository.findById(artistId);

        if (artist.isEmpty()) {
            return null;
        }

        UserArtistId userArtistId = new UserArtistId(userData.get().getId(), artist.get().getId());

        Optional<UserArtist> userArtist = userArtistRepository.findById(userArtistId);

        if (userArtist.isPresent()) {
            return getArtistInfoByArtist(username, artist.get());
        }

        UserArtist newUserArtist = new UserArtist();

        newUserArtist.setId(userArtistId);
        newUserArtist.setAddedDate(new Date());

        Integer maxArtistNumberInUserList = userArtistRepository.findMaxArtistNumberInUserList(userData.get().getId());
        if (maxArtistNumberInUserList == null) maxArtistNumberInUserList = 0;

        newUserArtist.setArtistNumberInUserList(maxArtistNumberInUserList + 1);

        userArtistRepository.save(newUserArtist);

        return getArtistInfoByArtist(username, artist.get());
    }

    public ArtistInfoDTO removeArtistFromUserList(String username, int artistId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Artist> artist = artistRepository.findById(artistId);

        if (artist.isEmpty()) {
            return null;
        }

        UserArtistId userArtistId = new UserArtistId(userData.get().getId(), artist.get().getId());

        Optional<UserArtist> userArtist = userArtistRepository.findById(userArtistId);

        if (userArtist.isEmpty()) {
            return getArtistInfoByArtist(username, artist.get());
        }

        userArtistRepository.delete(userArtist.get());

        List<UserArtist> userArtistList = userArtistRepository.findByIdUserIdAndArtistNumberInUserListGreaterThan(
                userData.get().getId(), userArtist.get().getArtistNumberInUserList());

        for (UserArtist userArtistItem : userArtistList) {
            userArtistItem.setArtistNumberInUserList(userArtistItem.getArtistNumberInUserList() - 1);
        }

        userArtistRepository.saveAll(userArtistList);

        return getArtistInfoByArtist(username, artist.get());
    }

    public ArtistInfoDTO rateArtist(String username, int artistId, int rateId) {
        Optional<UserRating> userRating = userRatingRepository.findById(rateId);

        if (userRating.isEmpty()) {
            return null;
        }

        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Artist> artist = artistRepository.findById(artistId);

        if (artist.isEmpty()) {
            return null;
        }

        UserArtistId userArtistId = new UserArtistId(userData.get().getId(), artist.get().getId());

        Optional<UserArtistRating> userArtistRating = userArtistRatingRepository.findById(userArtistId);

        if (userArtistRating.isPresent()) {
            userArtistRating.get().setUserRatingId(rateId);
            userArtistRatingRepository.save(userArtistRating.get());
            return getArtistInfoByArtist(username, artist.get());
        }

        UserArtistRating newUserArtistRating = new UserArtistRating();
        newUserArtistRating.setId(userArtistId);
        newUserArtistRating.setUserRatingId(rateId);

        userArtistRatingRepository.save(newUserArtistRating);
        return getArtistInfoByArtist(username, artist.get());
    }
}
