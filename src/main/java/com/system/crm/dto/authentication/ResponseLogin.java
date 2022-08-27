package com.system.crm.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseLogin {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
    private String token;

}
