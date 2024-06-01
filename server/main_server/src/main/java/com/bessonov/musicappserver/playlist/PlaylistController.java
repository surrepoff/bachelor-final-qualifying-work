package com.bessonov.musicappserver.playlist;

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

}
