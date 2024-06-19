package com.bessonov.musicappserver.utils;

public class StatusResponseDTO {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusResponseDTO{" +
                "status='" + status + '\'' +
                '}';
    }
}
