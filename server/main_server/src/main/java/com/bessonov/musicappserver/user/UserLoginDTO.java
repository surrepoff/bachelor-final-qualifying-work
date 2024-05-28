package com.bessonov.musicappserver.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    @Size(min = 8, message = "Minimum password length is 8 characters.")
    private String password;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty @Size(min = 8, message = "Minimum password length is 8 characters.") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty @Size(min = 8, message = "Minimum password length is 8 characters.") String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
