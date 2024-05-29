package com.bessonov.musicappserver.database.artistTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, ArtistTrackId> {
    public List<ArtistTrack> findByIdTrackIdOrderByIdArtistIdAsc(int trackId);
    public List<ArtistTrack> findByIdArtistIdOrderByIdTrackIdAsc(int artistId);
}
