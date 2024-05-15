package com.bessonov.musicappserver.database.artist_track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, ArtistTrackId> {

}
