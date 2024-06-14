package com.bessonov.musicappserver.database.userAlbum;

import java.util.Date;

public class UserAlbumDTO {
    private Boolean added;
    private Date addedDate;

    public UserAlbumDTO() {
        this.added = false;
        this.addedDate = new Date(0);
    }

    public UserAlbumDTO(UserAlbum userAlbum) {
        if (userAlbum == null) {
            this.added = false;
            this.addedDate = new Date(0);
        } else {
            this.added = true;
            this.addedDate = userAlbum.getAddedDate();
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
        return "UserAlbumDTO{" +
                "added=" + added +
                ", addedDate=" + addedDate +
                '}';
    }
}
