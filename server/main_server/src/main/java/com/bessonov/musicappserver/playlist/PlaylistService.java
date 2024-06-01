package com.bessonov.musicappserver.playlist;

import com.bessonov.musicappserver.database.playlist.Playlist;
import com.bessonov.musicappserver.database.playlist.PlaylistDTO;
import com.bessonov.musicappserver.database.playlist.PlaylistRepository;
import com.bessonov.musicappserver.database.playlistTrack.PlaylistTrack;
import com.bessonov.musicappserver.database.playlistTrack.PlaylistTrackRepository;
import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackRepository;
import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.bessonov.musicappserver.database.userData.UserDataShortDTO;
import com.bessonov.musicappserver.database.userPlaylist.UserPlaylist;
import com.bessonov.musicappserver.database.userPlaylist.UserPlaylistDTO;
import com.bessonov.musicappserver.database.userPlaylist.UserPlaylistId;
import com.bessonov.musicappserver.database.userPlaylist.UserPlaylistRepository;
import com.bessonov.musicappserver.database.userPlaylistAccessLevel.UserPlaylistAccessLevel;
import com.bessonov.musicappserver.database.userPlaylistAccessLevel.UserPlaylistAccessLevelDTO;
import com.bessonov.musicappserver.database.userPlaylistAccessLevel.UserPlaylistAccessLevelRepository;
import com.bessonov.musicappserver.database.userPlaylistRating.UserPlaylistRating;
import com.bessonov.musicappserver.database.userPlaylistRating.UserPlaylistRatingRepository;
import com.bessonov.musicappserver.database.userRating.UserRating;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingRepository;
import com.bessonov.musicappserver.database.userTrack.UserTrack;
import com.bessonov.musicappserver.database.userTrack.UserTrackDTO;
import com.bessonov.musicappserver.database.userTrack.UserTrackId;
import com.bessonov.musicappserver.database.userTrackRating.UserTrackRating;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserPlaylistRepository userPlaylistRepository;

    @Autowired
    private UserPlaylistAccessLevelRepository userPlaylistAccessLevelRepository;

    @Autowired
    private UserPlaylistRatingRepository userPlaylistRatingRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    public PlaylistInfoDTO getByPlaylistId(String username, int playlistId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        return playlist.map(value -> getPlaylistInfoByPlaylist(username, value)).orElse(null);
    }

    public List<PlaylistInfoDTO> getByPlaylistIdList(String username, List<Integer> playlistIdList) {
        List<PlaylistInfoDTO> playlistInfoDTOList = new ArrayList<>();

        for (Integer playlistId : playlistIdList) {
            playlistInfoDTOList.add(getByPlaylistId(username, playlistId));
        }

        return playlistInfoDTOList;
    }

    public List<PlaylistInfoDTO> getAll(String username) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Playlist> playlistList = playlistRepository.findAll(sort);

        List<PlaylistInfoDTO> playlistInfoDTOList = new ArrayList<>();

        for (Playlist playlist : playlistList) {
            playlistInfoDTOList.add(getPlaylistInfoByPlaylist(username, playlist));
        }

        return playlistInfoDTOList;
    }

    public List<PlaylistInfoDTO> getPlaylistUserList(String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "playlistNumberInUserList");

        List<UserPlaylist> userPlaylistList = userPlaylistRepository.findByIdUserId(userData.get().getId(), sort);

        List<PlaylistInfoDTO> playlistInfoDTOList = new ArrayList<>();

        for (UserPlaylist userPlaylist : userPlaylistList) {
            playlistInfoDTOList.add(getByPlaylistId(username, userPlaylist.getId().getPlaylistId()));
        }

        return playlistInfoDTOList;
    }

    public PlaylistInfoDTO getPlaylistInfoByPlaylist(String username, Playlist playlist) {
        PlaylistInfoDTO playlistInfoDTO = new PlaylistInfoDTO();

        playlistInfoDTO.setPlaylist(new PlaylistDTO(playlist));

        List<UserDataShortDTO> ownerList = new ArrayList<>();
        List<UserPlaylist> ownerUserPlaylistList = userPlaylistRepository.findByAccessLevelId(0);
        for (UserPlaylist userPlaylist : ownerUserPlaylistList) {
            Optional<UserData> userData = userDataRepository.findById(userPlaylist.getId().getUserId());
            userData.ifPresent(data -> ownerList.add(new UserDataShortDTO(data)));
        }
        playlistInfoDTO.setOwner(ownerList);

        List<Integer> trackIdList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.ASC, "trackNumberInPlaylist");
        List<PlaylistTrack> playlistTrackList = playlistTrackRepository.findByIdPlaylistId(playlist.getId(), sort);
        for (PlaylistTrack playlistTrack : playlistTrackList) {
            trackIdList.add(playlistTrack.getId().getTrackId());
        }
        playlistInfoDTO.setTrackId(trackIdList);

        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isPresent()){
            UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlist.getId());

            UserRatingDTO userRatingDTO = new UserRatingDTO();
            Optional<UserPlaylistRating> userPlaylistRating = userPlaylistRatingRepository.findById(userPlaylistId);
            if (userPlaylistRating.isPresent()) {
                Optional<UserRating> userRating = userRatingRepository.findById(userPlaylistRating.get().getUserRatingId());
                if (userRating.isPresent()) {
                    userRatingDTO = new UserRatingDTO(userRating.get());
                }
            }
            playlistInfoDTO.setRating(userRatingDTO);

            UserPlaylistAccessLevelDTO userPlaylistAccessLevelDTO = new UserPlaylistAccessLevelDTO();
            UserPlaylistDTO userPlaylistDTO = new UserPlaylistDTO();
            Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);
            if (userPlaylist.isPresent()) {
                Optional<UserPlaylistAccessLevel> userPlaylistAccessLevel = userPlaylistAccessLevelRepository.findById(userPlaylist.get().getAccessLevelId());
                if (userPlaylistAccessLevel.isPresent()) {
                    userPlaylistAccessLevelDTO = new UserPlaylistAccessLevelDTO(userPlaylistAccessLevel.get());
                }

                userPlaylistDTO = new UserPlaylistDTO(userPlaylist.get());
            }
            playlistInfoDTO.setAccessLevel(userPlaylistAccessLevelDTO);
            playlistInfoDTO.setIsAdded(userPlaylistDTO);
        }

        return playlistInfoDTO;
    }

    public UserPlaylistDTO addPlaylistToUserList(String username, int playlistId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlist.get().getId());

        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        if (userPlaylist.isPresent()) {
            return new UserPlaylistDTO(userPlaylist.get());
        }

        UserPlaylist newUserPlaylist = new UserPlaylist();

        newUserPlaylist.setId(userPlaylistId);
        newUserPlaylist.setAddedDate(new Date());
        newUserPlaylist.setAccessLevelId(2); // listener access level id

        Integer maxPlaylistNumberInUserList = userPlaylistRepository.findMaxPlaylistNumberInUserList(userData.get().getId());
        if (maxPlaylistNumberInUserList == null) maxPlaylistNumberInUserList = 0;

        newUserPlaylist.setPlaylistNumberInUserList(maxPlaylistNumberInUserList + 1);

        userPlaylistRepository.save(newUserPlaylist);

        return new UserPlaylistDTO(newUserPlaylist);
    }

    public UserPlaylistDTO removePlaylistFromUserList(String username, int playlistId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlist.get().getId());

        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        if (userPlaylist.isEmpty()) {
            return new UserPlaylistDTO();
        }

        userPlaylistRepository.delete(userPlaylist.get());

        List<UserPlaylist> userPlaylistList = userPlaylistRepository.findByIdUserIdAndPlaylistNumberInUserListGreaterThan(
                userData.get().getId(), userPlaylist.get().getPlaylistNumberInUserList());

        for (UserPlaylist userPlaylistItem : userPlaylistList) {
            userPlaylistItem.setPlaylistNumberInUserList(userPlaylistItem.getPlaylistNumberInUserList() - 1);
        }

        userPlaylistRepository.saveAll(userPlaylistList);

        return new UserPlaylistDTO();
    }

    public UserRatingDTO ratePlaylist(String username, int playlistId, int rateId) {
        Optional<UserRating> userRating = userRatingRepository.findById(rateId);

        if (userRating.isEmpty()) {
            return null;
        }

        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlist.get().getId());

        Optional<UserPlaylistRating> userPlaylistRating = userPlaylistRatingRepository.findById(userPlaylistId);

        if (userPlaylistRating.isPresent()) {
            userPlaylistRating.get().setUserRatingId(rateId);
            userPlaylistRatingRepository.save(userPlaylistRating.get());
            return new UserRatingDTO(userRating.get());
        }

        UserPlaylistRating newUserPlaylistRating = new UserPlaylistRating();
        newUserPlaylistRating.setId(userPlaylistId);
        newUserPlaylistRating.setUserRatingId(rateId);

        userPlaylistRatingRepository.save(newUserPlaylistRating);
        return new UserRatingDTO(userRating.get());
    }
}
