package com.bessonov.musicappserver;

import com.bessonov.musicappserver.model.track.Track;
import com.bessonov.musicappserver.model.track.TrackDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

@SpringBootTest
class MusicAppServerApplicationTests {
	@Test
	void contextLoads() {
	}

	@Autowired
	private TrackDAO trackDAO;
	/*
	@Test
	void saveTrack() {
		Track track = new Track();
		track.setPrimary_genre_id(0);
		track.setLicense_id(0);
		track.setName("test");
		track.setDuration_in_seconds(13);
		track.setRelease_date(new Date(13));
		trackDAO.save(track);
	}
	*/

	@Test
	void getAllTracks() {
		List<Track> trackList = trackDAO.getAllTracks();
		System.out.println(trackList);
	}
}
