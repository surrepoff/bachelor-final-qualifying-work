package com.bessonov.musicappserver.database.userArtist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArtistRepository extends JpaRepository<UserArtist, UserArtistId> {

}
