package com.bessonov.musicappserver.database.userData;

import java.util.Date;

public class UserDataDTO {
    private int id;
    private String username;
    private String email;
    private String nickname;
    private Date registrationDate;
    private Date lastUpdateDate;

    public UserDataDTO () {
        this.id = -1;
        this.username = "";
        this.email = "";
        this.nickname = "";
        this.registrationDate = new Date(0);
        this.lastUpdateDate = new Date(0);
    }

    public UserDataDTO (UserData userData) {
        if (userData == null) {
            this.id = -1;
            this.username = "";
            this.email = "";
            this.nickname = "";
            this.registrationDate = new Date(0);
            this.lastUpdateDate = new Date(0);
        } else {
            this.id = userData.getId();
            this.username = userData.getUsername();
            this.email = userData.getEmail();
            this.nickname = userData.getNickname();
            this.registrationDate = userData.getRegistrationDate();
            this.lastUpdateDate = userData.getLastUpdateDate();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "UserDataDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", registrationDate=" + registrationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
