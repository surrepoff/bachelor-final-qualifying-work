package com.bessonov.musicappserver.database.userArtist;

import java.util.Date;

public class UserArtistDTO {
    private Boolean added;
    private Date addedDate;

    public UserArtistDTO() {
        this.added = false;
        this.addedDate = new Date(0);
    }

    public UserArtistDTO(UserArtist userArtist) {
        if (userArtist == null) {
            this.added = false;
            this.addedDate = new Date(0);
        } else {
            this.added = true;
            this.addedDate = userArtist.getAddedDate();
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
        return "UserArtistDTO{" +
                "added=" + added +
                ", addedDate=" + addedDate +
                '}';
    }
}
