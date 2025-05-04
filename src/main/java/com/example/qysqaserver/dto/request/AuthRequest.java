package com.example.qysqaserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank(message = "validation.emailOrUsername.not-blank")
    @Size(min = 4, max = 100, message = "validation.emailOrUsername.size")
    private String emailOrUsername;

    @NotBlank(message = "validation.password.not-blank")
    @Size(min = 5, max = 50, message = "validation.password.size")
    private String password;
}
