package com.bessonov.musicappserver.database.userPlaylistAccessLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistAccessLevelRepository extends JpaRepository<UserPlaylistAccessLevel, Integer> {

}
