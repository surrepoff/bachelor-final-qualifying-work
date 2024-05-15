package com.bessonov.musicappserver;

import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.trackInfo.TrackInfoDTO;
import com.bessonov.musicappserver.trackInfo.TrackInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MusicAppServerApplicationTests {
	@Test
	void contextLoads() {
	}

	@Autowired
	private TrackInfoService trackInfoService;

	@Test
	void getTrackInfo() {
		List<TrackInfoDTO> trackInfoDTOList = trackInfoService.getTrackInfoAll();
		System.out.println(trackInfoDTOList);
	}
}
