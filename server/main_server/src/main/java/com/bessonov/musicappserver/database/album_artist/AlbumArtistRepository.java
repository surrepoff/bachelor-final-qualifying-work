package com.bessonov.musicappserver.database.album_artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumArtistRepository extends JpaRepository<AlbumArtist, AlbumArtistId> {

}
