package com.system.crm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.system.crm.domain.constants.CustomSequence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ADDRESS_GENERATOR)
    @SequenceGenerator(name = ADDRESS_GENERATOR, sequenceName = ADDRESS_SEQUENCE_NAME, allocationSize = SIZE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Region is not valid")
    @Column(nullable = false)
    private String addressName;

    @NotNull
    private boolean status;

    public Address(String addressName, boolean status) {
        this.addressName = addressName;
        this.status = status;
    }

}
