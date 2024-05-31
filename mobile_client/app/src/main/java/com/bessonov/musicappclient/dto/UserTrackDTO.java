package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.Date;

public class UserTrackDTO {
    private boolean isAdded;
    private Date addedDate;

    UserTrackDTO() {
        this.isAdded = false;
        this.addedDate = new Date(0);
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

    @NonNull
    @Override
    public String toString() {
        return "UserTrackDTO{" +
                "isAdded=" + isAdded +
                ", addedDate=" + addedDate +
                '}';
    }
}
