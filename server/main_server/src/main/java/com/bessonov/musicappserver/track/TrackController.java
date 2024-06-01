package com.bessonov.musicappserver.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/get/all")
    public List<TrackInfoDTO> getAll(Authentication authentication) {
        return trackService.getTrackInfoAll(authentication.getName());
    }

    @GetMapping("/get/{trackId}")
    public TrackInfoDTO getByTrackId(@PathVariable Integer trackId, Authentication authentication) {
        return trackService.getTrackInfoByTrackId(authentication.getName(), trackId);
    }

    @PostMapping("/get/list")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList, Authentication authentication) {
        return trackService.getTrackInfoByTrackIdList(authentication.getName(), trackIdList);
    }

    @GetMapping("/stream/{trackId}")
    public ResponseEntity<Resource> streamAudio(@PathVariable Integer trackId) {
        return trackService.streamAudio(trackId);
    }

    @GetMapping("/download/{trackId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer trackId) {
        return trackService.downloadFile(trackId);
    }
}
