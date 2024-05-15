package com.bessonov.musicappserver.model.artist_track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, ArtistTrackId> {

}
