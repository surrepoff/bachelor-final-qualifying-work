package com.bessonov.musicappserver.database.userAlbum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAlbumRepository extends JpaRepository<UserAlbum, UserAlbumId> {
    @Query("SELECT MAX(ual.albumNumberInUserList) FROM UserAlbum ual WHERE ual.Id.userId = :userId")
    Integer findMaxAlbumNumberInUserList(@Param("userId") int userId);

    public List<UserAlbum> findByIdUserIdAndAlbumNumberInUserListGreaterThan(int userId, int albumNumberInUserList);
}
