package com.bessonov.musicappserver.database.albumArtist;

import com.bessonov.musicappserver.database.artistTrack.ArtistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumArtistRepository extends JpaRepository<AlbumArtist, AlbumArtistId> {
    public List<AlbumArtist> findByIdAlbumIdOrderByIdArtistIdAsc(int albumId);
    public List<AlbumArtist> findByIdArtistIdOrderByIdAlbumIdAsc(int artistId);
}
