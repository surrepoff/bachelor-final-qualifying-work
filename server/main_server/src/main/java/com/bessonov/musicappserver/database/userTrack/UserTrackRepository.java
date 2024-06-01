package com.bessonov.musicappserver.database.userTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrackRepository extends JpaRepository<UserTrack, UserTrackId> {
    @Query("SELECT MAX(ut.trackNumberInUserList) FROM UserTrack ut WHERE ut.Id.userId = :userId")
    Integer findMaxTrackNumberInUserList(@Param("userId") int userId);

    public List<UserTrack> findByIdUserIdAndTrackNumberInUserListGreaterThan(int userId, int trackNumberInUserList);
}
