package com.bessonov.musicappserver.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @NotEmpty
    @Size(min = 4, message = "Логин должен быть минимум 4 символа")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "В логине могут быть использованы только цифры, буквы и символ '_'")
    private String username;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!@#$%^&*()-+=?]+$", message = "В пароле могут быть использованы только цифры, буквы и символы: '_', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=' и '?'")
    @Size(min = 8, message = "Пароль должен быть минимум 8 символов")
    private String password;

    public @NotEmpty @Size(min = 4, message = "Логин должен быть минимум 4 символа") @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "В логине могут быть использованы только цифры, буквы и символ '_'") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty @Size(min = 4, message = "Логин должен быть минимум 4 символа") @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "В логине могут быть использованы только цифры, буквы и символ '_'") String username) {
        this.username = username;
    }

    public @NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_!@#$%^&*()-+=?]+$", message = "В пароле могут быть использованы только цифры, буквы и символы: '_', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=' и '?'") @Size(min = 8, message = "Пароль должен быть минимум 8 символов") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_!@#$%^&*()-+=?]+$", message = "В пароле могут быть использованы только цифры, буквы и символы: '_', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=' и '?'") @Size(min = 8, message = "Пароль должен быть минимум 8 символов") String password) {
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
