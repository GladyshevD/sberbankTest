package ru.sberbank.onlinetest.to;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserTo {
    @NotBlank
    @Size(min = 6, max = 100)
    private String username;

    @Email
    @NotBlank
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @NotBlank
    @Size(min = 6, max = 255)
    private String name;
}
