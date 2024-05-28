package com.bessonov.musicappserver.database.userData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByEmail(String email);
}
