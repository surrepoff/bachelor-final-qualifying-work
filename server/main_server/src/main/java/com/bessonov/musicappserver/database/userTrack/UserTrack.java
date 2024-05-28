package com.bessonov.musicappserver.database.userTrack;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "user_track")
public class UserTrack {
    @EmbeddedId
    private UserTrackId id;

    @Column(name = "track_number_in_user_list")
    private int trackNumberInUserList;

    @Column(name = "added_date")
    private Date addedDate;

    public UserTrackId getId() {
        return id;
    }

    public void setId(UserTrackId id) {
        this.id = id;
    }

    public int getTrackNumberInUserList() {
        return trackNumberInUserList;
    }

    public void setTrackNumberInUserList(int trackNumberInUserList) {
        this.trackNumberInUserList = trackNumberInUserList;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "UserTrack{" +
                "id=" + id +
                ", trackNumberInUserList=" + trackNumberInUserList +
                ", addedDate=" + addedDate +
                '}';
    }
}
