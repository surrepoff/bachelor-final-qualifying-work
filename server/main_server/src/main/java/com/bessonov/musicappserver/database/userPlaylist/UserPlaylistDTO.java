package com.bessonov.musicappserver.database.userPlaylist;

import java.util.Date;

public class UserPlaylistDTO {
    private boolean isAdded;
    private Date addedDate;

    public UserPlaylistDTO() {
        this.isAdded = false;
        this.addedDate = new Date(0);
    }

    public UserPlaylistDTO(UserPlaylist userPlaylist) {
        if (userPlaylist == null) {
            this.isAdded = false;
            this.addedDate = new Date(0);
        }
        else {
            this.isAdded = true;
            this.addedDate = userPlaylist.getAddedDate();
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
        return "UserPlaylistDTO{" +
                "isAdded=" + isAdded +
                ", addedDate=" + addedDate +
                '}';
    }
}
