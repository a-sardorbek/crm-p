package com.system.crm.dto.client;

import com.system.crm.validation.annotation.NumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateClientStatus {

    @NumberValidation
    private Long id;

    @NotNull
    @Pattern(regexp = "^true$|^false$", message = "type allowed input: true or false")
    private String status;
}
