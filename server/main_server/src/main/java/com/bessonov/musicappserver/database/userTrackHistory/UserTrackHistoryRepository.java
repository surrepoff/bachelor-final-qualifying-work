package com.bessonov.musicappserver.database.userTrackHistory;

import com.bessonov.musicappserver.database.userTrack.UserTrackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackHistoryRepository extends JpaRepository<UserTrackHistory, UserTrackId> {

}
