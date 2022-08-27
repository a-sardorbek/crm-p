package com.system.crm.dto.userSystem;

import com.system.crm.validation.annotation.NumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemUserUpdatePasswordDto {


    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password should consist of minimum 6 characters")
    private String password;




}
