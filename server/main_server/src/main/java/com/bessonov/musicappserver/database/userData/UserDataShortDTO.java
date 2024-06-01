package com.bessonov.musicappserver.database.userData;

import java.util.Date;

public class UserDataShortDTO {
    private int id;
    private String nickname;

    public UserDataShortDTO () {
        this.id = -1;
        this.nickname = "";
    }

    public UserDataShortDTO (UserData userData) {
        if (userData == null) {
            this.id = -1;
            this.nickname = "";
        } else {
            this.id = userData.getId();
            this.nickname = userData.getNickname();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserDataShortDTO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
