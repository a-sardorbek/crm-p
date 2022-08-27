package com.system.crm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static com.system.crm.domain.constants.CustomSequence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = CLIENT_GENERATOR)
    @SequenceGenerator(name=CLIENT_GENERATOR, sequenceName = CLIENT_SEQUENCE_NAME,allocationSize = SIZE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;

    @Column(nullable = false)
    private String problem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "professionId")
    private ResponsibleProfession responsibleProfession;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "systemUserId")
    private SystemUser systemUser;

    @Column(name = "created_date", columnDefinition = "DATE")
    private LocalDate created_date;

    @Column(name = "created_time", columnDefinition = "TIME")
    private LocalTime created_time;

    @Column(nullable = false)
    private boolean status;

    @Column(name = "house_num")
    private String houseNum;

    @Column(name = "flat_num")
    private String flatNum;

}
