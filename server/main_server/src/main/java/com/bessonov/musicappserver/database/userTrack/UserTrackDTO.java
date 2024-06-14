package com.bessonov.musicappserver.database.userTrack;

import java.util.Date;

public class UserTrackDTO {
    private Boolean added;
    private Date addedDate;

    public UserTrackDTO() {
        this.added = false;
        this.addedDate = new Date(0);
    }

    public UserTrackDTO(UserTrack userTrack) {
        if (userTrack == null) {
            this.added = false;
            this.addedDate = new Date(0);
        } else {
            this.added = true;
            this.addedDate = userTrack.getAddedDate();
        }
    }

    public Boolean isAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "UserTrackDTO{" +
                "added=" + added +
                ", addedDate=" + addedDate +
                '}';
    }
}
