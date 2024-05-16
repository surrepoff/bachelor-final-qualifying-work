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

    @GetMapping("/api/trackInfo/get/all")
    public List<TrackInfoDTO> getAll() {
        return trackInfoService.getTrackInfoAll();
    }

    @PostMapping("/api/trackInfo/get/byTrackId")
    public TrackInfoDTO getByTrackId(@RequestBody int trackId) {
        return trackInfoService.getTrackInfoByTrackId(trackId);
    }

    @PostMapping("/api/trackInfo/get/byTrackId/list")
    public List<TrackInfoDTO> getByTrackIdList(@RequestBody List<Integer> trackIdList) {
        return trackInfoService.getTrackInfoByTrackIdList(trackIdList);
    }
}
