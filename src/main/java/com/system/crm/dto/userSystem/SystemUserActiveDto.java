package com.system.crm.dto.userSystem;

import com.system.crm.validation.annotation.NumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemUserActiveDto {

    @NumberValidation
    private Long id;

    @NotNull
    @Pattern(regexp = "^true$|^false$", message = "allowed inputs: true or false")
    private String isActive;
}
