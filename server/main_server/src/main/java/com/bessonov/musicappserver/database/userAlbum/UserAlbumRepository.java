package com.bessonov.musicappserver.database.userAlbum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAlbumRepository extends JpaRepository<UserAlbum, UserAlbumId> {

}
