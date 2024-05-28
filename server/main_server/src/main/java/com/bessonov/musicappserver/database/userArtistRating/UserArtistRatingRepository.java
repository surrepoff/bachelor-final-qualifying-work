package com.bessonov.musicappserver.database.userArtistRating;

import com.bessonov.musicappserver.database.userArtist.UserArtistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArtistRatingRepository extends JpaRepository<UserArtistRating, UserArtistId> {

}
