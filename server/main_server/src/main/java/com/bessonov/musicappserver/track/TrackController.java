package com.bessonov.musicappserver.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    TrackService trackService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/get/all")
    public List<TrackInfoDTO> getAll(Authentication authentication) {
        return trackService.getTrackInfoAll();
    }

    @PostMapping("/get/byTrackId")
    public TrackInfoDTO getByTrackId(@RequestBody int trackId, Authentication authentication) {
        return trackService.getTrackInfoByTrackId(trackId);
    }

    @PostMapping("/get/byTrackId/list")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList, Authentication authentication) {
        return trackService.getTrackInfoByTrackIdList(trackIdList);
    }
}
