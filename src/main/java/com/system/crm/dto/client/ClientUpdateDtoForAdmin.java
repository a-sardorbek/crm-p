package com.system.crm.dto.client;

import com.system.crm.validation.annotation.NumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientUpdateDtoForAdmin {

    @NumberValidation
    private Long clientId;

    @NumberValidation
    private Long systemUserId;

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

    @NotBlank(message = "Problem cannot be empty")
    private String problem;

}
