package com.bessonov.musicappserver.database.userPlaylist;

import java.util.Date;

public class UserPlaylistDTO {
    private Boolean added;
    private Date addedDate;

    public UserPlaylistDTO() {
        this.added = false;
        this.addedDate = new Date(0);
    }

    public UserPlaylistDTO(UserPlaylist userPlaylist) {
        if (userPlaylist == null) {
            this.added = false;
            this.addedDate = new Date(0);
        }
        else {
            this.added = true;
            this.addedDate = userPlaylist.getAddedDate();
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
        return "UserPlaylistDTO{" +
                "added=" + added +
                ", addedDate=" + addedDate +
                '}';
    }
}
