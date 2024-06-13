package com.bessonov.musicappserver.user;

public class UserResponseDTO {
    private int status_code;
    private String message;

    public UserResponseDTO(String message, int status_code) {
        this.message = message;
        this.status_code = status_code;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                '}';
    }
}
