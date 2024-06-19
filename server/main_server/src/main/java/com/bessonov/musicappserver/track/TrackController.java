package com.bessonov.musicappserver.track;

import com.bessonov.musicappserver.database.userTrackHistory.UserTrackHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @GetMapping("/get/all")
    public List<TrackInfoDTO> getAll(Authentication authentication) {
        return trackService.getAll(authentication.getName());
    }

    @GetMapping("/get/{trackId}")
    public TrackInfoDTO getByTrackId(@PathVariable Integer trackId, Authentication authentication) {
        return trackService.getByTrackId(authentication.getName(), trackId);
    }

    @PostMapping("/get/list")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList, Authentication authentication) {
        return trackService.getByTrackIdList(authentication.getName(), trackIdList);
    }

    @GetMapping("/get/user")
    public List<TrackInfoDTO> getTrackUserList(Authentication authentication) {
        return trackService.getTrackUserList(authentication.getName());
    }

    @GetMapping("/get/history")
    public List<TrackInfoDTO> getTrackHistoryList(Authentication authentication) {
        return trackService.getTrackHistoryList(authentication.getName());
    }

    @GetMapping("/add/{trackId}")
    public TrackInfoDTO addTrackToUserList(@PathVariable Integer trackId, Authentication authentication) {
        return trackService.addTrackToUserList(authentication.getName(), trackId);
    }

    @GetMapping("/add/history/{trackId}")
    public UserTrackHistoryDTO addTrackToHistoryList(@PathVariable Integer trackId, Authentication authentication) {
        return trackService.addTrackToHistoryList(authentication.getName(), trackId);
    }

    @GetMapping("/remove/{trackId}")
    public TrackInfoDTO removeTrackFromUserList(@PathVariable Integer trackId, Authentication authentication) {
        return trackService.removeTrackFromUserList(authentication.getName(), trackId);
    }

    @PostMapping("/rate/{trackId}")
    public TrackInfoDTO rateTrack(@PathVariable Integer trackId, @RequestBody Integer rateId, Authentication authentication) {
        return trackService.rateTrack(authentication.getName(), trackId, rateId);
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
