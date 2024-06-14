package com.bessonov.musicappserver.database.userTrackHistory;

import com.bessonov.musicappserver.database.userTrack.UserTrackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrackHistoryRepository extends JpaRepository<UserTrackHistory, UserTrackId> {
    List<UserTrackHistory> findTop10ByIdUserIdOrderByListenDateDesc(int userId);
}
