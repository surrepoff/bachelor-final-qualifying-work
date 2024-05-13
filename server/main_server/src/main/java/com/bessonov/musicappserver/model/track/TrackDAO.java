package com.bessonov.musicappserver.model.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackDAO {
    @Autowired
    private TrackRepository repository;

    public void save(Track track) {
        repository.save(track);
    }

    public List<Track> getAllTracks() {
        List<Track> tracks = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(tracks::add);
        return tracks;
    }
}
