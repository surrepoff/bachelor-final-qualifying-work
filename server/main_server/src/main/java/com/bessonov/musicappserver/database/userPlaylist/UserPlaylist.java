package com.bessonov.musicappserver.database.userPlaylist;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "user_playlist")
public class UserPlaylist {
    @EmbeddedId
    private UserPlaylistId id;

    @Column(name = "access_level_id")
    private int accessLevelId;

    @Column(name = "playlist_number_in_user_list")
    private int playlistNumberInUserList;

    @Column(name = "added_date")
    private Date addedDate;

    public UserPlaylistId getId() {
        return id;
    }

    public void setId(UserPlaylistId id) {
        this.id = id;
    }

    public int getAccessLevelId() {
        return accessLevelId;
    }

    public void setAccessLevelId(int accessLevelId) {
        this.accessLevelId = accessLevelId;
    }

    public int getPlaylistNumberInUserList() {
        return playlistNumberInUserList;
    }

    public void setPlaylistNumberInUserList(int playlistNumberInUserList) {
        this.playlistNumberInUserList = playlistNumberInUserList;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "UserPlaylist{" +
                "id=" + id +
                ", accessLevelId=" + accessLevelId +
                ", playlistNumberInUserList=" + playlistNumberInUserList +
                ", addedDate=" + addedDate +
                '}';
    }
}
