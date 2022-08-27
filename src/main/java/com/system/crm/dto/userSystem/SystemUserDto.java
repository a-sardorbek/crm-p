package com.system.crm.dto.userSystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemUserDto {

    @NotBlank(message = "First name is not valid")
    private String firstName;

    @NotBlank(message = "Last name is not valid")
    private String lastName;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(max = 9, min = 9, message = "Phone number should consists of 9 digits ex: 991234567")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password should consist of minimum 6 characters")
    private String password;


    @NotNull
    @Pattern(regexp = "^true$|^false$", message = "admin type allowed input: true or false")
    private String isSuperAdmin;

}
