package com.bessonov.musicappserver.database.userPlaylistRating;

import com.bessonov.musicappserver.database.userPlaylist.UserPlaylistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistRatingRepository extends JpaRepository<UserPlaylistRating, UserPlaylistId> {
    void deleteByIdPlaylistId(int playlistId);
}
