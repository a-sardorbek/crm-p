package com.system.crm.dto.userSystem;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.crm.validation.annotation.NumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemUserUpdateAdminDto {

    @NumberValidation
    private Long id;

    @NotBlank(message = "First name is not valid")
    private String firstName;

    @NotBlank(message = "Last name is not valid")
    private String lastName;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(max = 9, min = 9, message = "Phone number should consists of 9 digits ex: 991234567")
    private String phoneNumber;




}
