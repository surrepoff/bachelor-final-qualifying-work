package com.bessonov.musicappserver.database.artist_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistStatusRepository extends JpaRepository<ArtistStatus, Integer> {

}
