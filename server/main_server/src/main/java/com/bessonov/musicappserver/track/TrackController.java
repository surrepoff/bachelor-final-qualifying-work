package com.bessonov.musicappserver.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    TrackService trackService;

    @GetMapping("/get/all")
    public List<TrackInfoDTO> getAll() {
        return trackService.getTrackInfoAll();
    }

    @PostMapping("/get/byTrackId")
    public TrackInfoDTO getByTrackId(@RequestBody int trackId) {
        return trackService.getTrackInfoByTrackId(trackId);
    }

    @PostMapping("/get/byTrackId/list")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList) {
        return trackService.getTrackInfoByTrackIdList(trackIdList);
    }
}
