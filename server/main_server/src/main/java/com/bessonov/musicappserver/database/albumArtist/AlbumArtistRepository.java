package com.bessonov.musicappserver.database.albumArtist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumArtistRepository extends JpaRepository<AlbumArtist, AlbumArtistId> {
    List<AlbumArtist> findByIdAlbumIdOrderByIdArtistIdAsc(int albumId);

    List<AlbumArtist> findByIdArtistIdOrderByIdAlbumIdAsc(int artistId);
}
