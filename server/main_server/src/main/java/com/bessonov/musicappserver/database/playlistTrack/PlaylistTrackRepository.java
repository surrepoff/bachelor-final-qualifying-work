package com.bessonov.musicappserver.database.playlistTrack;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackId> {
    @Query("SELECT MAX(pt.trackNumberInPlaylist) FROM PlaylistTrack pt WHERE pt.Id.playlistId = :playlistId")
    Integer findMaxTrackNumberInPlaylist(@Param("playlistId") int playlistId);

    List<PlaylistTrack> findByIdPlaylistIdOrderByTrackNumberInPlaylistAsc(int playlistId);
    List<PlaylistTrack> findByIdPlaylistIdAndTrackNumberInPlaylistGreaterThan(int playlistId, int trackNumberInPlaylist);

    @Transactional
    void deleteByIdPlaylistId(int playlistId);
}
