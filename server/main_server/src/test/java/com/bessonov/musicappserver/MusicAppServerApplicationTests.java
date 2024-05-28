package com.bessonov.musicappserver;

import com.bessonov.musicappserver.track.TrackInfoDTO;
import com.bessonov.musicappserver.track.TrackService;
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
	private TrackService trackService;

	@Test
	void getTrackInfo() {
		List<TrackInfoDTO> trackInfoDTOList = trackService.getTrackInfoAll();
		System.out.println(trackInfoDTOList);
	}
}
