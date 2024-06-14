package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.Date;

public class UserTrackHistoryDTO {
    private Date listenDate;

    public UserTrackHistoryDTO() {
        this.listenDate = new Date(0);
    }

    public UserTrackHistoryDTO(Date listenDate) {
        this.listenDate = listenDate;
    }

    public Date getListenDate() {
        return listenDate;
    }

    public void setListenDate(Date listenDate) {
        this.listenDate = listenDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserTrackHistoryDTO{" +
                "listenDate=" + listenDate +
                '}';
    }
}
