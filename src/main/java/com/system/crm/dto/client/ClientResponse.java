package com.system.crm.dto.client;

import com.system.crm.domain.entity.Address;
import com.system.crm.domain.entity.Address;
import com.system.crm.domain.entity.ResponsibleProfession;
import com.system.crm.dto.userSystem.SystemUserInfoProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientResponse {


    private Long id;

    private String firstName;

    private String lastName;

    private Integer phoneNumber;

    private Address address;

    private String problem;

    private ResponsibleProfession responsibleProfession;

    private SystemUserInfoProfileDto systemUserInfoProfileDto;

    private boolean status;

    private LocalDate created_date;

    private LocalTime created_time;

    private String houseNum;

    private String flatNum;


}
