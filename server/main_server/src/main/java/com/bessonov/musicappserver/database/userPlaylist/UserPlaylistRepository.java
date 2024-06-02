package com.bessonov.musicappserver.database.userPlaylist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, UserPlaylistId> {
    @Query("SELECT MAX(up.playlistNumberInUserList) FROM UserPlaylist up WHERE up.Id.userId = :userId")
    Integer findMaxPlaylistNumberInUserList(@Param("userId") int userId);

    public List<UserPlaylist> findByIdUserIdOrderByPlaylistNumberInUserListAsc(int userId);
    public List<UserPlaylist> findByIdUserIdAndPlaylistNumberInUserListGreaterThan(int userId, int playlistNumberInUserList);
    public List<UserPlaylist> findByIdPlaylistIdAndAccessLevelIdOrderByAddedDateAsc(int playlistId, int accessLevelId);

    void deleteByIdPlaylistId(int playlistId);
}
