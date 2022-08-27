package com.system.crm.repository;

import com.system.crm.domain.entity.ResponsibleProfession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsibleProfessionRepository extends JpaRepository<ResponsibleProfession,Long> {
    Optional<ResponsibleProfession> findByNameProfession(String nameProfession);
}
