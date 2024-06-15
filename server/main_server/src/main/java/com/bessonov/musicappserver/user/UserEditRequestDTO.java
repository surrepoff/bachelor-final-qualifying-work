package com.bessonov.musicappserver.user;

import java.util.Map;

public class UserEditRequestDTO {
    private Map<String, String> editMap;
    private String password;

    public Map<String, String> getEditMap() {
        return editMap;
    }

    public void setEditMap(Map<String, String> editMap) {
        this.editMap = editMap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEditRequestDTO{" +
                "editMap=" + editMap +
                ", password='" + password + '\'' +
                '}';
    }
}
