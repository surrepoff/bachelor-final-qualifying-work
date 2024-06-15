package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        return "UserEditResponseDTO{" +
                "editMap=" + editMap +
                '}';
    }
}
