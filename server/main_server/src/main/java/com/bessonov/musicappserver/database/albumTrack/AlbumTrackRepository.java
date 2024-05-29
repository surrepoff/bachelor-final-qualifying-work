package com.bessonov.musicappserver.database.albumTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumTrackRepository extends JpaRepository<AlbumTrack, AlbumTrackId> {
    public List<AlbumTrack> findByIdTrackIdOrderByIdAlbumIdAsc(int trackId);
    public Optional<AlbumTrack> findFirstByIdTrackIdOrderByIdAlbumIdAsc(int trackId);
    public List<AlbumTrack> findByIdAlbumIdOrderByTrackNumberInAlbumAsc(int albumId);
}
