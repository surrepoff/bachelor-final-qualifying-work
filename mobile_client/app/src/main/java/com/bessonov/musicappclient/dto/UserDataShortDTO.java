package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

public class UserDataShortDTO {
    private int id;
    private String nickname;

    public UserDataShortDTO() {
        this.id = -1;
        this.nickname = "";
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

    @NonNull
    @Override
    public String toString() {
        return "UserDataShortDTO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
