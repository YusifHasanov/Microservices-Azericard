package org.azerciard.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserRegisterRequest {

    @NotBlank(message = "Name cannot be empty")
    String name;

    @NotBlank(message = "Surname cannot be empty")
    String surname;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    String email;

    @Pattern(regexp = "^(?=.*[0-9]).{6,}$", message = "Password must be at least 6 characters long and contain at least one number")
    @NotBlank(message = "Password cannot be empty")
    String password;

    @NotBlank(message = "ConfirmPassword cannot be empty")
    String confirmPassword;

    @NotBlank(message = "PhoneNumber cannot be empty")
    String phoneNumber;

}
