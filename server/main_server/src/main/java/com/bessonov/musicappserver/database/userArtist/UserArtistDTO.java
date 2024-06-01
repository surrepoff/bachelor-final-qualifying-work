package com.bessonov.musicappserver.database.userArtist;

import java.util.Date;

public class UserArtistDTO {
    private boolean isAdded;
    private Date addedDate;

    public UserArtistDTO() {
        this.isAdded = false;
        this.addedDate = new Date(0);
    }

    public UserArtistDTO(UserArtist userArtist) {
        if (userArtist == null) {
            this.isAdded = false;
            this.addedDate = new Date(0);
        }
        else {
            this.isAdded = true;
            this.addedDate = userArtist.getAddedDate();
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
        return "UserArtistDTO{" +
                "isAdded=" + isAdded +
                ", addedDate=" + addedDate +
                '}';
    }
}
