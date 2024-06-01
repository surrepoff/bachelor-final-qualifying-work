package com.bessonov.musicappserver.database.userPlaylist;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, UserPlaylistId> {
    public List<UserPlaylist> findByIdUserId(int userId, Sort sort);
    public List<UserPlaylist> findByAccessLevelId(int accessLevelId);
}
