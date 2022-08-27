package com.system.crm.dto.client;

import com.system.crm.validation.annotation.NumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientUpdateDto {

    @NumberValidation
    private Long clientId;

    @NumberValidation
    private Long professionId;

    @NumberValidation
    private Long addressId;

    @NotBlank(message = "First name is not valid")
    private String firstName;

    @NotBlank(message = "Last name is not valid")
    private String lastName;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(max = 9, min = 9, message = "Phone number should consists of 9 digits ex: 991234567")
    private String phoneNumber;

    @NotBlank(message = "houseNum cannot be empty")
    private String houseNum;

    @NotBlank(message = "flatNum cannot be empty")
    private String flatNum;


    @NotBlank(message = "Problem cannot be empty")
    private String problem;

    @NotNull
    @Pattern(regexp = "^true$|^false$", message = "type allowed input: true or false")
    private String status;



}
