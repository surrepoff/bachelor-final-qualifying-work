package com.bessonov.musicappserver.playlist;

import com.bessonov.musicappserver.database.userPlaylist.UserPlaylistDTO;
import com.bessonov.musicappserver.database.userRating.UserRatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/get/all")
    public List<PlaylistInfoDTO> getAll(Authentication authentication) {
        return playlistService.getAll(authentication.getName());
    }

    @GetMapping("/get/{playlistId}")
    public PlaylistInfoDTO getByPlaylistId(@PathVariable Integer playlistId, Authentication authentication) {
        return playlistService.getByPlaylistId(authentication.getName(), playlistId);
    }

    @PostMapping("/get/list")
    public List<PlaylistInfoDTO> getByPlaylistIdList(@RequestBody List<Integer> playlistIdList, Authentication authentication) {
        return playlistService.getByPlaylistIdList(authentication.getName(), playlistIdList);
    }

    @GetMapping("/get/user")
    public List<PlaylistInfoDTO> getPlaylistUserList(Authentication authentication) {
        return playlistService.getPlaylistUserList(authentication.getName());
    }

    @GetMapping("/add/{playlistId}")
    public UserPlaylistDTO addPlaylistToUserList(@PathVariable Integer playlistId, Authentication authentication) {
        return playlistService.addPlaylistToUserList(authentication.getName(), playlistId);
    }

    @GetMapping("/remove/{playlistId}")
    public UserPlaylistDTO removePlaylistFromUserList(@PathVariable Integer playlistId, Authentication authentication) {
        return playlistService.removePlaylistFromUserList(authentication.getName(), playlistId);
    }

    @PostMapping("/rate/{playlistId}")
    public UserRatingDTO ratePlaylist(@PathVariable Integer playlistId, @RequestBody Integer rateId, Authentication authentication) {
        return playlistService.ratePlaylist(authentication.getName(), playlistId, rateId);
    }

    @PostMapping("/create")
    public PlaylistInfoDTO createPlaylist(@RequestBody PlaylistCreateDTO playlistCreateDTO, Authentication authentication) {
        return playlistService.createPlaylist(authentication.getName(), playlistCreateDTO);
    }

    @GetMapping("/delete/{playlistId}")
    public PlaylistInfoDTO deletePlaylist(@PathVariable Integer playlistId, Authentication authentication) {
        return playlistService.deletePlaylist(authentication.getName(), playlistId);
    }

    @PostMapping("/edit/{playlistId}/rename")
    public PlaylistInfoDTO editPlaylistRename(@PathVariable Integer playlistId, @RequestBody String newPlaylistName, Authentication authentication) {
        return playlistService.editPlaylistRename(authentication.getName(), playlistId, newPlaylistName);
    }

    @GetMapping("/edit/{playlistId}/track/add/{trackId}")
    public PlaylistInfoDTO editPlaylistAddTrack(@PathVariable Integer playlistId, @PathVariable Integer trackId, Authentication authentication) {
        return playlistService.editPlaylistAddTrack(authentication.getName(), playlistId, trackId);
    }

    @GetMapping("/edit/{playlistId}/track/remove/{trackId}")
    public PlaylistInfoDTO editPlaylistRemoveTrack(@PathVariable Integer playlistId, @PathVariable Integer trackId, Authentication authentication) {
        return playlistService.editPlaylistRemoveTrack(authentication.getName(), playlistId, trackId);
    }

    @PostMapping("/edit/{playlistId}/accessLevel/{userId}")
    public PlaylistInfoDTO editPlaylistUserAccessLevel(@PathVariable Integer playlistId, @PathVariable Integer userId, @RequestBody Integer newAccessLevelId, Authentication authentication) {
        return playlistService.editPlaylistUserAccessLevel(authentication.getName(), playlistId, userId, newAccessLevelId);
    }
}
