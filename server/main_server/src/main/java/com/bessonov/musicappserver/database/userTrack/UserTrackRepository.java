package com.bessonov.musicappserver.database.userTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackRepository extends JpaRepository<UserTrack, UserTrackId> {

}
