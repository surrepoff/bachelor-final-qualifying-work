package com.bessonov.musicappserver.database.userArtist;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "user_artist")
public class UserArtist {
    @EmbeddedId
    private UserArtistId id;

    @Column(name = "user_rating_id")
    private int userRatingId;

    @Column(name = "artist_number_in_user_list")
    private int artistNumberInUserList;

    @Column(name = "added_date")
    private Date addedDate;

    public UserArtistId getId() {
        return id;
    }

    public void setId(UserArtistId id) {
        this.id = id;
    }

    public int getUserRatingId() {
        return userRatingId;
    }

    public void setUserRatingId(int userRatingId) {
        this.userRatingId = userRatingId;
    }

    public int getArtistNumberInUserList() {
        return artistNumberInUserList;
    }

    public void setArtistNumberInUserList(int artistNumberInUserList) {
        this.artistNumberInUserList = artistNumberInUserList;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "UserArtist{" +
                "id=" + id +
                ", userRatingId=" + userRatingId +
                ", artistNumberInUserList=" + artistNumberInUserList +
                ", addedDate=" + addedDate +
                '}';
    }
}
