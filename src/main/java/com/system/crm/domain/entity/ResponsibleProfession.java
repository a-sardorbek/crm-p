package com.system.crm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Objects;

import static com.system.crm.domain.constants.CustomSequence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsibleProfession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PROFESSION_GENERATOR)
    @SequenceGenerator(name=PROFESSION_GENERATOR, sequenceName = PROFESSION_SEQUENCE_NAME,allocationSize = SIZE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Responsible profession is not valid")
    @Column(nullable = false)
    private String nameProfession;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsibleProfession that = (ResponsibleProfession) o;
        return Objects.equals(id, that.id) && Objects.equals(nameProfession, that.nameProfession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameProfession);
    }
}
