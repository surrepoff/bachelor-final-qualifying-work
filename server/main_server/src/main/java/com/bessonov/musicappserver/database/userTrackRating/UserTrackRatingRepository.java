package com.bessonov.musicappserver.database.userTrackRating;

import com.bessonov.musicappserver.database.userTrack.UserTrackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackRatingRepository extends JpaRepository<UserTrackRating, UserTrackId> {

}
