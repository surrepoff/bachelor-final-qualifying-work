package com.bessonov.musicappserver.database.userAlbum;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "user_album")
public class UserAlbum {
    @EmbeddedId
    private UserAlbumId id;

    @Column(name = "album_number_in_user_list")
    private int albumNumberInUserList;

    @Column(name = "added_date")
    private Date addedDate;

    public UserAlbumId getId() {
        return id;
    }

    public void setId(UserAlbumId id) {
        this.id = id;
    }

    public int getAlbumNumberInUserList() {
        return albumNumberInUserList;
    }

    public void setAlbumNumberInUserList(int albumNumberInUserList) {
        this.albumNumberInUserList = albumNumberInUserList;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "UserAlbum{" +
                "id=" + id +
                ", albumNumberInUserList=" + albumNumberInUserList +
                ", addedDate=" + addedDate +
                '}';
    }
}
