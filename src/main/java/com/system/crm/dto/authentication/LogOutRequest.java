package com.system.crm.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequest {

    @Valid
    @NotNull(message = "User phone cannot be empty")
    private String userPhone;

    @Valid
    @NotNull(message = "Existing Token needs to be passed")
    private String token;
}
