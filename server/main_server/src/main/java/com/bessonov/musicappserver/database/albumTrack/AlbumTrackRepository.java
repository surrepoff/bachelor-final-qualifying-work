package com.bessonov.musicappserver.database.albumTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumTrackRepository extends JpaRepository<AlbumTrack, AlbumTrackId> {

}
