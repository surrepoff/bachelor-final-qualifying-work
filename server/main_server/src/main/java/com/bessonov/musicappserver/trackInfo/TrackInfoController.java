package com.bessonov.musicappserver.trackInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrackInfoController {
    @Autowired
    TrackInfoService trackInfoService;

    @GetMapping("/api/trackinfo/get/all")
    public List<TrackInfoDTO> getAll() {
        return trackInfoService.getTrackInfoAll();
    }

    @PostMapping("/api/trackinfo/get/bytrackid")
    public TrackInfoDTO getByTrackId(@RequestBody int trackId) {
        return trackInfoService.getTrackInfoByTrackId(trackId);
    }

    @PostMapping("/api/trackinfo/get/bytrackidlist")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList) {
        return trackInfoService.getTrackInfoByTrackIdList(trackIdList);
    }
}
