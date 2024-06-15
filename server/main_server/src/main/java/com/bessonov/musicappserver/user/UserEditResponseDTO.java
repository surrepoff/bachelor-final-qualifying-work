package com.bessonov.musicappserver.user;

import java.util.Map;

public class UserEditResponseDTO {
    private Map<String, String> editMap;

    public UserEditResponseDTO(Map<String, String> editMap) {
        this.editMap = editMap;
    }

    public Map<String, String> getEditMap() {
        return editMap;
    }

    public void setEditMap(Map<String, String> editMap) {
        this.editMap = editMap;
    }

    @Override
    public String toString() {
        return "UserEditResponseDTO{" +
                "editMap=" + editMap +
                '}';
    }
}
