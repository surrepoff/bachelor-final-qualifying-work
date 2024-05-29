package com.bessonov.musicappserver.database.userData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    public Optional<UserData> findByUsername(String username);
    public Optional<UserData> findByEmail(String email);
}
