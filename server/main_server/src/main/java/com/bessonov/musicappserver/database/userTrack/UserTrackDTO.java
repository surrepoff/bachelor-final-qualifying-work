package com.bessonov.musicappserver.database.userTrack;

import java.util.Date;

public class UserTrackDTO {
    private boolean isAdded;
    private Date addedDate;

    public UserTrackDTO() {
        this.isAdded = false;
        this.addedDate = new Date(0);
    }

    public UserTrackDTO(UserTrack userTrack) {
        if (userTrack == null) {
            this.isAdded = false;
            this.addedDate = new Date(0);
        }
        else {
            this.isAdded = true;
            this.addedDate = userTrack.getAddedDate();
        }
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
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
                "isAdded=" + isAdded +
                ", addedDate=" + addedDate +
                '}';
    }
}
