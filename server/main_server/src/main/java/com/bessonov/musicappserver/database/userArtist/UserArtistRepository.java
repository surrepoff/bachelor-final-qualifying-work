package com.bessonov.musicappserver.database.userArtist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserArtistRepository extends JpaRepository<UserArtist, UserArtistId> {
    @Query("SELECT MAX(uar.artistNumberInUserList) FROM UserArtist uar WHERE uar.Id.userId = :userId")
    Integer findMaxArtistNumberInUserList(@Param("userId") int userId);

    public List<UserArtist> findByIdUserIdAndArtistNumberInUserListGreaterThan(int userId, int artistNumberInUserList);

}
