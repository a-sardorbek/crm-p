package com.system.crm.domain.entity;

import com.system.crm.domain.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.system.crm.domain.constants.CustomSequence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SYSTEM_GENERATOR)
    @SequenceGenerator(name=SYSTEM_GENERATOR, sequenceName = SYSTEM_SEQUENCE_NAME,allocationSize = SIZE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "userId",nullable = false)
    private String customSystemUserId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "created_date",nullable = false)
    private LocalDate localDate;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isDeleted;


}
