package com.system.crm.dto.userSystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemUserInfoProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
