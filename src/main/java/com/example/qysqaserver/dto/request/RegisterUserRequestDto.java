package com.example.qysqaserver.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequestDto {

    @NotNull(message = "register-user.username.not-null")
    @JsonProperty("username")
    private String username;

    @Size(min = 5, message = "register-user.password.size")
    @NotNull(message = "register-user.password.not-null")
    @JsonProperty("password")
    private String password;

    @Size(min = 5, message = "register-user.confirmPassword.size")
    @NotNull(message = "register-user.confirmPassword.not-null")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @NotNull(message = "register-user.email.not-null")
    @JsonProperty("email")
    private String email;
}