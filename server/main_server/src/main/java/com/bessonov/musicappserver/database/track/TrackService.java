package com.bessonov.musicappserver.database.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackService {
    @Autowired
    private TrackRepository repository;

    public void save(Track track) {
        repository.save(track);
    }

    public List<Track> getAllTracks() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");;
        List<Track> tracks = new ArrayList<>();
        Streamable.of(repository.findAll(sort))
                .forEach(tracks::add);
        return tracks;
    }
}
