package com.bessonov.musicappserver.database.userTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserTrackId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "track_id")
    private int trackId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "UserTrackId{" +
                "userId=" + userId +
                ", trackId=" + trackId +
                '}';
    }
}
