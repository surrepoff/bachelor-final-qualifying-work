package com.bessonov.musicappserver.database.artistStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistStatusRepository extends JpaRepository<ArtistStatus, Integer> {

}
