package com.bessonov.musicappserver.database.userAlbum;

import java.util.Date;

public class UserAlbumDTO {
    private boolean isAdded;
    private Date addedDate;

    public UserAlbumDTO() {
        this.isAdded = false;
        this.addedDate = new Date(0);
    }

    public UserAlbumDTO(UserAlbum userAlbum) {
        if (userAlbum == null) {
            this.isAdded = false;
            this.addedDate = new Date(0);
        }
        else {
            this.isAdded = true;
            this.addedDate = userAlbum.getAddedDate();
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
        return "UserAlbumDTO{" +
                "isAdded=" + isAdded +
                ", addedDate=" + addedDate +
                '}';
    }
}
