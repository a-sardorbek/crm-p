package com.system.crm.service;

import com.system.crm.domain.entity.ResponsibleProfession;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ResponsibleProfessionService {

    void createProfession(Set<ResponsibleProfession> responsibleProfession);
    ResponsibleProfession updateProfession(ResponsibleProfession responsibleProfession);
    Optional<ResponsibleProfession> getProfessionById(String id);
    List<ResponsibleProfession> getAllProfessions();
    void deleteProfession(Long id);
}
