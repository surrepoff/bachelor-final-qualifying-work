package com.bessonov.musicappserver.database.userTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserTrackId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "track_id")
    private int trackId;

    public UserTrackId() {
    }

    public UserTrackId(int userId, int trackId) {
        this.userId = userId;
        this.trackId = trackId;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTrackId that = (UserTrackId) o;
        return userId == that.userId && trackId == that.trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, trackId);
    }

    @Override
    public String toString() {
        return "UserTrackId{" +
                "userId=" + userId +
                ", trackId=" + trackId +
                '}';
    }
}
