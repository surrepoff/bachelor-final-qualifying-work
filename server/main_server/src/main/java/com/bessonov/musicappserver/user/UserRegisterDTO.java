package com.bessonov.musicappserver.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 8, message = "Minimum password length is 8 characters.")
    private String password;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty @Size(min = 8, message = "Minimum password length is 8 characters.") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty @Size(min = 8, message = "Minimum password length is 8 characters.") String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
