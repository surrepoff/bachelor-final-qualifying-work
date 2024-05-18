package com.bessonov.musicappserver.database.albumTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumTrackRepository extends JpaRepository<AlbumTrack, AlbumTrackId> {
    List<AlbumTrack> findByIdTrackIdOrderByIdAlbumIdAsc(int trackId);
    List<AlbumTrack> findByIdAlbumIdOrderByTrackNumberInAlbumAsc(int albumId);
}
