package com.bessonov.musicappserver.database.userTrackHistory;

import com.bessonov.musicappserver.database.userTrack.UserTrackId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "user_track_history")
public class UserTrackHistory {
    @EmbeddedId
    private UserTrackId id;

    @Column(name = "listen_date")
    private Date listenDate;

    public UserTrackId getId() {
        return id;
    }

    public void setId(UserTrackId id) {
        this.id = id;
    }

    public Date getListenDate() {
        return listenDate;
    }

    public void setListenDate(Date listenDate) {
        this.listenDate = listenDate;
    }

    @Override
    public String toString() {
        return "UserTrackHistory{" +
                "id=" + id +
                ", listenDate=" + listenDate +
                '}';
    }
}
