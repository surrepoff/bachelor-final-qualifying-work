package com.bessonov.musicappserver.playlist;

import com.bessonov.musicappserver.database.playlist.Playlist;
import com.bessonov.musicappserver.database.playlist.PlaylistDTO;
import com.bessonov.musicappserver.database.playlist.PlaylistRepository;
import com.bessonov.musicappserver.database.playlistTrack.PlaylistTrack;
import com.bessonov.musicappserver.database.playlistTrack.PlaylistTrackId;
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
public class PlaylistService {
    private final int ownerAccessId = 0;
    private final int moderatorAccessId = 1;
    private final int listenerAccessId = 2;
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
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        return playlist.map(value -> getPlaylistInfoByPlaylist(username, value)).orElse(null);
    }

    public List<PlaylistInfoDTO> getByPlaylistIdList(String username, List<Integer> playlistIdList) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        List<PlaylistInfoDTO> playlistInfoDTOList = new ArrayList<>();

        for (Integer playlistId : playlistIdList) {
            playlistInfoDTOList.add(getByPlaylistId(username, playlistId));
        }

        return playlistInfoDTOList;
    }

    public List<PlaylistInfoDTO> getAll(String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

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

        List<UserPlaylist> userPlaylistList = userPlaylistRepository.findByIdUserIdOrderByPlaylistNumberInUserListAsc(userData.get().getId());

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
        List<UserPlaylist> ownerUserPlaylistList = userPlaylistRepository.findByIdPlaylistIdAndAccessLevelIdOrderByAddedDateAsc(playlist.getId(), ownerAccessId);
        for (UserPlaylist userPlaylist : ownerUserPlaylistList) {
            Optional<UserData> userData = userDataRepository.findById(userPlaylist.getId().getUserId());
            userData.ifPresent(data -> ownerList.add(new UserDataShortDTO(data)));
        }
        playlistInfoDTO.setOwner(ownerList);

        List<Integer> trackIdList = new ArrayList<>();
        List<PlaylistTrack> playlistTrackList = playlistTrackRepository.findByIdPlaylistIdOrderByTrackNumberInPlaylistAsc(playlist.getId());
        for (PlaylistTrack playlistTrack : playlistTrackList) {
            trackIdList.add(playlistTrack.getId().getTrackId());
        }
        playlistInfoDTO.setTrackId(trackIdList);

        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isPresent()) {
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
        newUserPlaylist.setAccessLevelId(listenerAccessId);

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

        if (userPlaylist.get().getAccessLevelId() == ownerAccessId) {
            List<UserPlaylist> ownerUserPlaylistList = userPlaylistRepository.findByIdPlaylistIdAndAccessLevelIdOrderByAddedDateAsc(playlistId, ownerAccessId);

            if (ownerUserPlaylistList.isEmpty()) {
                List<UserPlaylist> moderatorUserPlaylistList = userPlaylistRepository.findByIdPlaylistIdAndAccessLevelIdOrderByAddedDateAsc(playlistId, moderatorAccessId);

                if (moderatorUserPlaylistList.isEmpty()) {
                    List<UserPlaylist> listenerUserPlaylistList = userPlaylistRepository.findByIdPlaylistIdAndAccessLevelIdOrderByAddedDateAsc(playlistId, listenerAccessId);

                    if (listenerUserPlaylistList.isEmpty()) {
                        playlistTrackRepository.deleteByIdPlaylistId(playlistId);
                        userPlaylistRepository.deleteByIdPlaylistId(playlistId);
                        userPlaylistRatingRepository.deleteByIdPlaylistId(playlistId);
                        playlistRepository.deleteById(playlistId);
                    } else {
                        listenerUserPlaylistList.get(0).setAccessLevelId(ownerAccessId);
                        userPlaylistRepository.saveAll(listenerUserPlaylistList);
                    }
                } else {
                    moderatorUserPlaylistList.get(0).setAccessLevelId(ownerAccessId);
                    userPlaylistRepository.saveAll(moderatorUserPlaylistList);
                }
            }
        }

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

    public PlaylistInfoDTO createPlaylist(String username, PlaylistCreateDTO playlistCreateDTO) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Playlist playlist = new Playlist();
        playlist.setName(playlistCreateDTO.getName());
        playlist.setCreationDate(new Date());
        playlist.setLastUpdateDate(new Date());

        playlist = playlistRepository.save(playlist);

        int trackNumberInPlaylist = 1;
        for (Integer trackId : playlistCreateDTO.getTrackId()) {
            Optional<Track> track = trackRepository.findById(trackId);

            if (track.isPresent()) {
                PlaylistTrack playlistTrack = new PlaylistTrack();
                playlistTrack.setId(new PlaylistTrackId(playlist.getId(), track.get().getId()));
                playlistTrack.setTrackNumberInPlaylist(trackNumberInPlaylist);
                playlistTrackRepository.save(playlistTrack);
                trackNumberInPlaylist++;
            }
        }

        UserPlaylist userPlaylist = new UserPlaylist();
        userPlaylist.setId(new UserPlaylistId(userData.get().getId(), playlist.getId()));
        userPlaylist.setAccessLevelId(ownerAccessId);
        userPlaylist.setAddedDate(new Date());

        Integer maxPlaylistNumberInUserList = userPlaylistRepository.findMaxPlaylistNumberInUserList(userData.get().getId());
        if (maxPlaylistNumberInUserList == null) maxPlaylistNumberInUserList = 0;

        userPlaylist.setPlaylistNumberInUserList(maxPlaylistNumberInUserList + 1);

        userPlaylistRepository.save(userPlaylist);

        return getPlaylistInfoByPlaylist(username, playlist);
    }

    public PlaylistInfoDTO deletePlaylist(String username, int playlistId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlistId);
        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        if (userPlaylist.isEmpty()) {
            return null;
        }

        if (userPlaylist.get().getAccessLevelId() != ownerAccessId) {
            return null;
        }

        playlistTrackRepository.deleteByIdPlaylistId(playlistId);
        userPlaylistRepository.deleteByIdPlaylistId(playlistId);
        userPlaylistRatingRepository.deleteByIdPlaylistId(playlistId);
        playlistRepository.deleteById(playlistId);

        return new PlaylistInfoDTO();
    }

    public PlaylistInfoDTO editPlaylistRename(String username, int playlistId, String newPlaylistName) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlistId);
        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        if (userPlaylist.isEmpty()) {
            return null;
        }

        if (userPlaylist.get().getAccessLevelId() != ownerAccessId && userPlaylist.get().getAccessLevelId() != moderatorAccessId) {
            return null;
        }

        playlist.get().setName(newPlaylistName);
        playlist.get().setLastUpdateDate(new Date());

        return getPlaylistInfoByPlaylist(username, playlistRepository.save(playlist.get()));
    }

    public PlaylistInfoDTO editPlaylistAddTrack(String username, int playlistId, int trackId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlistId);
        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        if (userPlaylist.isEmpty()) {
            return null;
        }

        if (userPlaylist.get().getAccessLevelId() != ownerAccessId && userPlaylist.get().getAccessLevelId() != moderatorAccessId) {
            return null;
        }

        PlaylistTrackId playlistTrackId = new PlaylistTrackId(playlistId, trackId);
        Optional<PlaylistTrack> playlistTrack = playlistTrackRepository.findById(playlistTrackId);

        if (playlistTrack.isPresent()) {
            return getPlaylistInfoByPlaylist(username, playlist.get());
        }

        Integer maxTrackNumberInPlaylist = playlistTrackRepository.findMaxTrackNumberInPlaylist(playlistId);
        if (maxTrackNumberInPlaylist == null) maxTrackNumberInPlaylist = 0;

        PlaylistTrack newPlaylistTrack = new PlaylistTrack();
        newPlaylistTrack.setId(playlistTrackId);
        newPlaylistTrack.setTrackNumberInPlaylist(maxTrackNumberInPlaylist + 1);

        playlistTrackRepository.save(newPlaylistTrack);

        return getPlaylistInfoByPlaylist(username, playlist.get());
    }

    public PlaylistInfoDTO editPlaylistRemoveTrack(String username, int playlistId, int trackId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlistId);
        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        if (userPlaylist.isEmpty()) {
            return null;
        }

        if (userPlaylist.get().getAccessLevelId() != ownerAccessId && userPlaylist.get().getAccessLevelId() != moderatorAccessId) {
            return null;
        }

        PlaylistTrackId playlistTrackId = new PlaylistTrackId(playlistId, trackId);
        Optional<PlaylistTrack> playlistTrack = playlistTrackRepository.findById(playlistTrackId);

        if (playlistTrack.isEmpty()) {
            return getPlaylistInfoByPlaylist(username, playlist.get());
        }

        playlistTrackRepository.delete(playlistTrack.get());

        List<PlaylistTrack> playlistTrackList = playlistTrackRepository.findByIdPlaylistIdAndTrackNumberInPlaylistGreaterThan(
                playlistId, playlistTrack.get().getTrackNumberInPlaylist());

        for (PlaylistTrack playlistTrackItem : playlistTrackList) {
            playlistTrackItem.setTrackNumberInPlaylist(playlistTrackItem.getTrackNumberInPlaylist() - 1);
        }

        playlistTrackRepository.saveAll(playlistTrackList);

        return getPlaylistInfoByPlaylist(username, playlist.get());
    }

    public PlaylistInfoDTO editPlaylistUserAccessLevel(String username, int playlistId, int userId, int newAccessLevelId) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);
        Optional<UserData> otherUserData = userDataRepository.findById(userId);

        if (userData.isEmpty() || otherUserData.isEmpty()) {
            return null;
        }

        if (userData.get().getId() == otherUserData.get().getId()) {
            return null;
        }

        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (playlist.isEmpty()) {
            return null;
        }

        UserPlaylistId userPlaylistId = new UserPlaylistId(userData.get().getId(), playlistId);
        Optional<UserPlaylist> userPlaylist = userPlaylistRepository.findById(userPlaylistId);

        UserPlaylistId otherUserPlaylistId = new UserPlaylistId(userId, playlistId);
        Optional<UserPlaylist> otherUserPlaylist = userPlaylistRepository.findById(otherUserPlaylistId);

        if (userPlaylist.isEmpty() || otherUserPlaylist.isEmpty()) {
            return null;
        }

        if (userPlaylist.get().getAccessLevelId() != ownerAccessId) {
            return null;
        }

        if (newAccessLevelId != ownerAccessId && newAccessLevelId != moderatorAccessId && newAccessLevelId != listenerAccessId) {
            return null;
        }

        otherUserPlaylist.get().setAccessLevelId(newAccessLevelId);

        userPlaylistRepository.save(otherUserPlaylist.get());

        return getPlaylistInfoByPlaylist(username, playlist.get());
    }
}
