package com.bessonov.musicappserver.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrackController {
    @Autowired
    TrackService trackService;

    @GetMapping("/api/track/get/all")
    public List<TrackInfoDTO> getAll() {
        return trackService.getTrackInfoAll();
    }

    @PostMapping("/api/track/get/byTrackId")
    public TrackInfoDTO getByTrackId(@RequestBody int trackId) {
        return trackService.getTrackInfoByTrackId(trackId);
    }

    @PostMapping("/api/track/get/byTrackId/list")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList) {
        return trackService.getTrackInfoByTrackIdList(trackIdList);
    }
}
