package com.system.crm.dto.userSystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemUserInfoDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate createdDate;
    private boolean isActive;


}
