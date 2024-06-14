package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

public class LicenseDTO {
    private int id;
    private String name;

    public LicenseDTO() {
        this.id = -1;
        this.name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "LicenseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
