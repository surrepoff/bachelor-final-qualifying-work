package com.bessonov.musicappserver.database.userAlbumRating;

import com.bessonov.musicappserver.database.userAlbum.UserAlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAlbumRatingRepository extends JpaRepository<UserAlbumRating, UserAlbumId> {

}
