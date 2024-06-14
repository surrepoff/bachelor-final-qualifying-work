package com.bessonov.musicappserver.database.userTrackHistory;

import java.util.Date;

public class UserTrackHistoryDTO {
    private Date listenDate;

    public UserTrackHistoryDTO() {
        this.listenDate = new Date(0);
    }

    public UserTrackHistoryDTO(Date listenDate) {
        this.listenDate = listenDate;
    }

    public UserTrackHistoryDTO(UserTrackHistory userTrackHistory) {
        if (userTrackHistory == null) {
            this.listenDate = new Date(0);
        } else {
            this.listenDate = userTrackHistory.getListenDate();
        }
    }

    public Date getListenDate() {
        return listenDate;
    }

    public void setListenDate(Date listenDate) {
        this.listenDate = listenDate;
    }

    @Override
    public String toString() {
        return "UserTrackHistoryDTO{" +
                "listenDate=" + listenDate +
                '}';
    }
}
