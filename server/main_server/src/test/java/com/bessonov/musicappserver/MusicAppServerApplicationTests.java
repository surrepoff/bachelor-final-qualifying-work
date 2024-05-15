package com.bessonov.musicappserver;

import com.bessonov.musicappserver.database.track.Track;
import com.bessonov.musicappserver.database.track.TrackService;
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
	void getAllTracks() {
		List<Track> trackList = trackService.getAllTracks();
		System.out.println(trackList);
	}
}
