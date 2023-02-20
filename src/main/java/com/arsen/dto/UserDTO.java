package com.arsen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Имя не может быть пустым!")
    @Size(max = 30, message = "Превышено максимальное допустимое значение для имени!")
    private String name;

    @NotEmpty(message = "Email не может быть пустым!")
    @Email(message = "Некорректный email!")
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "Пароль должен содержать больше 8 символов!")
    private String password;
}
